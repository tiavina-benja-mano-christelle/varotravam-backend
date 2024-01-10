package mg.company.varotravam.models;

import mg.company.varotravam.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 * Marque
 */
public class Marque {
    int id;
    String nom;

    //--GETTERS && SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    //--CONSTRUCTORS
    public Marque() {
    }

    public Marque(String nom) {
        this.setNom(nom);
    }

    public Marque(int id, String nom) {
        this.setId(id);
        this.setNom(nom);
    }

//--FONCTION
    public void saveMarque(Connection connection) throws Exception {
        boolean wasConnected = true;

        if (connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "insert into marque (id, nom) values(default, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, getNom());
            statement.executeUpdate();
        } catch (Exception e) {e.printStackTrace();
        } finally {if (!wasConnected) {connection.close();}}
    }

    public Vector<Marque> getAllMarque(Connection connection) throws Exception {
        Vector<Marque> marque = new Vector<>();
        boolean wasConnected = true;

        if (connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "select * from marque";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Marque m = new Marque();
                m.setId(resultSet.getInt("id"));
                m.setNom(resultSet.getString("nom"));
                marque.add(m);
            }
        } catch (Exception e) {e.printStackTrace();
        } finally {if (!wasConnected) {connection.close();}}
        return marque;
    }

    public Marque findById(Connection connection, int id) throws Exception {
        Marque m = null;
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "SELECT * FROM marque WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    m = new Marque();
                    m.setId(rs.getInt("id"));
                    m.setNom(rs.getString("nom"));
                }
            return m;
        } catch (Exception e) {e.printStackTrace();
        } finally {if (!wasConnected) {connection.close();}}
        return m;
    }
    public  void updateMarque(Connection connection,  int id,String nomM)throws Exception{
        boolean wasConnected = true;
        try{
            if (connection == null) {
                wasConnected = false;
                connection = DBConnection.getConnection();
            }
            Statement stmt = connection.createStatement();
            String sql = "UPDATE marque set  nom ='"+nomM+"' and   where  id ="+id;
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {e.printStackTrace();
        } finally {if (!wasConnected) {connection.close();}}
    }
}