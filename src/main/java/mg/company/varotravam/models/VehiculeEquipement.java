package mg.company.varotravam.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import mg.company.varotravam.utils.DBConnection;

public class VehiculeEquipement {
    int vehiculeId;
    int equipementId;

    public void save(Connection connection) throws SQLException {
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "INSERT INTO \"public\".vehicule_equipement( vehicule_id, equipement_id) VALUES ( ?, ? )";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, this.getVehiculeId());
            stmt.setInt(2, this.getEquipementId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    

    public VehiculeEquipement(int vehiculeId, int equipementId) {
        this.vehiculeId = vehiculeId;
        this.equipementId = equipementId;
    }



    public int getVehiculeId() {
        return vehiculeId;
    }
    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }
    public int getEquipementId() {
        return equipementId;
    }
    public void setEquipementId(int equipementId) {
        this.equipementId = equipementId;
    }

    
}
