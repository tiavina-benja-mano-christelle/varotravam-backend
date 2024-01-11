package mg.company.varotravam.models;

import java.sql.SQLException;
import java.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mg.company.varotravam.utils.DBConnection;

public class Carburant {
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

    public Carburant(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Carburant() {
    }

    public Vector<Carburant> getAllCarburant(Connection connection) throws SQLException, ClassNotFoundException{
        Vector<Carburant> carburants = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "select * from carburant";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Carburant carburant = new Carburant();
                carburant.setId(resultSet.getInt("id"));
                carburant.setNom(resultSet.getString("nom"));
                carburants.add(carburant);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return carburants;
    }

    public Carburant findById (Connection connection, int id) throws  SQLException,Exception{
        Carburant model = null;
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        try {
            String sql = "SELECT * FROM carburant WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    model = new Carburant();
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

    public void saveCarburant(Connection connection) throws SQLException, ClassNotFoundException {
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "insert into carburant(id, nom) values(default, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) 
        {
            statement.setString(1, this.getNom());
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