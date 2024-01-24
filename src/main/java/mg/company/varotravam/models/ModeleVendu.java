package mg.company.varotravam.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.Connection;

import mg.company.varotravam.utils.DBConnection;

public class ModeleVendu {
    int modele_id;
    String modele;
    int nombre_ventes;
    int etat;

    public int getModele_id() {
        return modele_id;
    }

    public void setModele_id(int modele_id) {
        this.modele_id = modele_id;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
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

    public ModeleVendu(int modele_id, String modele, int nombre_ventes, int etat) {
        this.modele_id = modele_id;
        this.modele = modele;
        this.nombre_ventes = nombre_ventes;
        this.etat = etat;
    }

    public ModeleVendu(int etat) {
        this.etat = etat;
    }

    public ModeleVendu() {
    }

    public Vector<ModeleVendu> getModeleVendu(Connection connection) throws ClassNotFoundException, SQLException{
        Vector<ModeleVendu> models = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "select * from v_modele_vendu where etat = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, this.getEtat());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ModeleVendu model = new ModeleVendu();
                    model.setModele_id(resultSet.getInt("modele_id"));
                    model.setModele(resultSet.getString("modele"));
                    model.setNombre_ventes(resultSet.getInt("nombre_ventes"));
                    model.setEtat(resultSet.getInt("etat"));
                    models.add(model);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return models;
    }
}
