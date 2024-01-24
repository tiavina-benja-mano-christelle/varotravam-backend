package mg.company.varotravam.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mg.company.varotravam.utils.DBConnection;

public class Commission {
    int id;
    Date commissionDate;
    double valeur;


    /**
     * Fonction pour prévoir un changement de commission
     * @param connection
     * @throws SQLException
     */
    public void save(Connection connection) throws SQLException {
        boolean wasConnected = connection == null;
        if(!wasConnected) connection = DBConnection.getConnection();
        String sql = "INSERT INTO commission (id, commission_date, valeur) VALUES (default, ?, ?) RETURNING id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, this.getCommissionDate());
            stmt.setDouble(2, this.getValeur());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) this.setId(rs.getInt("id"));
        } catch (SQLException e) {
            throw e;
        } finally {
            if(!wasConnected) connection.close();
        }
    }

    /**
     * Fonction pour récupérer la commission actuel
     * @param connection
     * @return
     * @throws SQLException
     */
    public Commission findActual(Connection connection) throws SQLException {
        Commission model = null;
        boolean wasConnected = connection == null;
        if(!wasConnected) connection = DBConnection.getConnection();
        String sql = "SELECT * FROM v_actual_commission";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
            ) {
            if (rs.next()) {
                model = new Commission();
                model.setId(rs.getInt("id"));
                model.setCommissionDate(rs.getDate("commission_date"));
                model.setValeur(rs.getDouble("valeur"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if(!wasConnected) connection.close();
        }
        return model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCommissionDate() {
        return commissionDate;
    }

    public void setCommissionDate(Date commissionDate) {
        this.commissionDate = commissionDate;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }
    
    
}
