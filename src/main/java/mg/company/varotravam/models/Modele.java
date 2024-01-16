package mg.company.varotravam.models;

import mg.company.varotravam.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Modele {
    int id,marqueId;
    String nom;
//--GETTERS && SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarqueId() {
        return marqueId;
    }

    public void setMarqueId(int marqueId) {
        this.marqueId = marqueId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
//--CONSTRUCTORS
    public Modele() {}
    public Modele(String nom, int marqueId) {
        this.setMarqueId(marqueId);
        this.setNom(nom);
    }
    public Modele(int id, String nom, int marqueId) {
        this.setId(id);
        this.setMarqueId(marqueId);
        this.setNom(nom);
    }
//--FONCTION
public void save(Connection connection) throws Exception {
    boolean wasConnected = true;

    if (connection == null) {
        wasConnected = false;
        connection = DBConnection.getConnection();
    }
    String sql = "insert into modele (id,nom,marque_id) values(default, ?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, getNom());
        statement.setInt(2, getMarqueId());
        statement.executeUpdate();
    } catch (Exception e) {e.printStackTrace();
    } finally {if (!wasConnected) {connection.close();}}
}

    public Vector<Modele> getAll(Connection connection) throws Exception {
        Vector<Modele> marque = new Vector<>();
        boolean wasConnected = true;

        if (connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "select * from modele";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Modele m = new Modele();
                m.setId(resultSet.getInt("id"));
                m.setNom(resultSet.getString("nom"));
                m.setMarqueId(resultSet.getInt("marque_id"));
                marque.add(m);
            }
        } catch (Exception e) {throw e;
        } finally {if (!wasConnected) {connection.close();}}
        return marque;
    }

    public Modele findById(Connection connection, int id) throws Exception {
        Modele m = null;
        boolean wasConnected = true;
        if (connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "SELECT * FROM modele WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                m = new Modele();
                m.setId(rs.getInt("id"));
                m.setNom(rs.getString("nom"));
                m.setMarqueId(rs.getInt("marque_id"));
            }
            return m;
        } catch (Exception e) {e.printStackTrace();
        } finally {if (!wasConnected) {connection.close();}}
        return m;
    }
    public  void update(Connection connection,  int id,String nomM,int marqueIdM)throws Exception{
        boolean wasConnected = true;
        try{
            if (connection == null) {
                wasConnected = false;
                connection = DBConnection.getConnection();
            }
            Statement stmt = connection.createStatement();
            String sql = "UPDATE modele set nom ='"+nomM+"' and marque_id ="+marqueIdM+"  where  id ="+id;
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {e.printStackTrace();
        } finally {if (!wasConnected) {connection.close();}}
    }

}
