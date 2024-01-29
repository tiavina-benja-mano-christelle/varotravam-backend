package mg.company.varotravam.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mg.company.varotravam.utils.DBConnection;

public class VehiculeImage {
    int id;
    int vehiculeId;
    String valeur;

    /**
     * Sauvegarder l'image de la voiture
     * @param connection
     */
    public void save(Connection connection) throws SQLException{
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "INSERT INTO \"public\".vehicule_image( vehicule_id, valeur) VALUES ( ?, ? ) RETURNING id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, this.getVehiculeId());
            stmt.setString(2, this.getValeur());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                this.setId(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    public VehiculeImage(int vehiculeId, String valeur) {
        this.vehiculeId = vehiculeId;
        this.valeur = valeur;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getVehiculeId() {
        return vehiculeId;
    }
    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }
    public String getValeur() {
        return valeur;
    }
    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    
}
