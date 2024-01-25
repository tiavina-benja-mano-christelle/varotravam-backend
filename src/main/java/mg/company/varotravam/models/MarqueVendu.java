package mg.company.varotravam.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.Connection;

import mg.company.varotravam.utils.DBConnection;

public class MarqueVendu {
    int marque_id;
    String marque;
    int nombre_ventes;
    int etat;

    public int getMarque_id() {
        return marque_id;
    }

    public void setMarque_id(int marque_id) {
        this.marque_id = marque_id;
    }
    
    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getNombre_ventes() {
        return nombre_ventes;
    }

    public void setNombre_ventes(int nombre_ventes) {
        this.nombre_ventes = nombre_ventes;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public MarqueVendu(int marque_id, String marque, int nombre_ventes, int etat) {
        this.marque_id = marque_id;
        this.marque = marque;
        this.nombre_ventes = nombre_ventes;
        this.etat = etat;
    }

    public MarqueVendu(int etat) {
        this.etat = etat;
    }

    public MarqueVendu() {
    }
    
    public Vector<MarqueVendu> getMarqueVendu(Connection connection) throws ClassNotFoundException, SQLException{
        Vector<MarqueVendu> marques = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "select * from v_marque_vendu where etat = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, this.getEtat());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MarqueVendu marque = new MarqueVendu();
                    marque.setMarque_id(resultSet.getInt("marque_id"));
                    marque.setMarque(resultSet.getString("marque"));
                    marque.setNombre_ventes(resultSet.getInt("nombre_ventes"));
                    marque.setEtat(resultSet.getInt("etat"));
                    marques.add(marque);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return marques;
    }
}
