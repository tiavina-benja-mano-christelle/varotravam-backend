package mg.company.varotravam.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.Connection;

import mg.company.varotravam.utils.DBConnection;

public class MeilleurVendeur {
    int utilisateur_id;
    String nom;
    int nombre_vente;
    int etat;

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNombre_vente() {
        return nombre_vente;
    }

    public void setNombre_vente(int nombre_vente) {
        this.nombre_vente = nombre_vente;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public MeilleurVendeur(int utilisateur_id, String nom, int nombre_vente, int etat) {
        this.utilisateur_id = utilisateur_id;
        this.nom = nom;
        this.nombre_vente = nombre_vente;
        this.etat = etat;
    }

    public MeilleurVendeur(int etat) {
        this.etat = etat;
    }

    public MeilleurVendeur() {
    }
    
    public Vector<MeilleurVendeur> getMeilleurVendeur(Connection connection) throws ClassNotFoundException, SQLException{
        Vector<MeilleurVendeur> vendeures = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "select * from v_meilleur_vendeur where etat = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, this.getEtat());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MeilleurVendeur vendeur = new MeilleurVendeur();
                    vendeur.setUtilisateur_id(resultSet.getInt("utilisateur_id"));
                    vendeur.setNom(resultSet.getString("nom"));
                    vendeur.setNombre_vente(resultSet.getInt("nombre_vente"));
                    vendeur.setEtat(resultSet.getInt("etat"));
                    vendeures.add(vendeur);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return vendeures;
    }
}
