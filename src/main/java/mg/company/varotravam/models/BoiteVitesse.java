package mg.company.varotravam.models;

import java.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mg.company.varotravam.utils.DBConnection;

public class BoiteVitesse {
    int id;
    String nom;

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

    public BoiteVitesse(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public BoiteVitesse() {
    }

    public Vector<BoiteVitesse> getAllBoiteVitesse(Connection connection){
        Vector<BoiteVitesse> vitesses = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "select * from boite_vitesse";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                BoiteVitesse vitesse = new BoiteVitesse();
                vitesse.setId(resultSet.getInt("id"));
                vitesse.setNom(resultSet.getString("nom"));
                vitesses.add(vitesse);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return vitesses;
    }

    public BoiteVitesse findById (Connection connection, int id) throws Exception{
        BoiteVitesse model = null;
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        try {
            String sql = "SELECT * FROM boite_vitesse WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    model = new BoiteVitesse();
                    model.setId(rs.getInt("id"));
                    model.setNom(rs.getString("nom"));
                    return model;
                } 
            }
            throw new Exception("Error");
        } catch(Exception ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    public void saveBoiteVitesse(Connection connection, String nom) throws SQLException {
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "insert into boite_vitesse(id, nom) values(default, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nom);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }
}