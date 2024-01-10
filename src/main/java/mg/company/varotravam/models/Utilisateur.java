package mg.company.varotravam.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mg.company.varotravam.utils.DBConnection;

public class Utilisateur {
    int id;
    String nom;
    String email;
    String motDePasse;
    boolean administrateur;

    /**
     * Récupère l'utilisateur par son identifiant
     * @param id
     * @param connection
     * @return
     */
    public static Utilisateur chercherParId(int id, Connection connection) {
        //TODO: implementer la recherche d'utilisateur par ID
        return new Utilisateur();
    }

    public static Utilisateur verifierAdministrateur(String nom, String password) throws Exception {
        try {
            Utilisateur utilisateur = chercherAdministrateurParNomOuEmail(nom, null);
            utilisateur.verifier(password);
            return utilisateur;
        } catch (Exception e) {
            throw e;
        }
    }

    public static Utilisateur verifierUtilisateur(String nom, String password) throws Exception {
        try {
            Utilisateur utilisateur = chercherUtilisateurParNomOuEmail(nom, null);
            utilisateur.verifier(password);
            return utilisateur;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean verifier(String motDePasse) throws Exception {
        if (this.getMotDePasse().equals(motDePasse)) {
            return true;
        }
        throw new Exception("Mot de passe érronée");
    }
    
    public static Utilisateur chercherUtilisateurParNomOuEmail(String valeur, Connection connection) throws Exception{
        Utilisateur model = null;
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        try {
            String sql = "SELECT * FROM v_utilisateur_client WHERE email=? OR nom=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, valeur);
                stmt.setString(2, valeur);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    model = new Utilisateur();
                    model.setId(rs.getInt("id"));
                    model.setNom(rs.getString("nom"));
                    model.setEmail(rs.getString("email"));
                    model.setMotDePasse(rs.getString("mot_de_passe"));
                    model.setAdministrateur(rs.getBoolean("administrateur"));
                    return model;
                } 
            } 
            throw new Exception("Email inconnue");
        } catch(Exception ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    public static Utilisateur chercherAdministrateurParNomOuEmail(String valeur, Connection connection) throws Exception{
        Utilisateur model = null;
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        try {
            String sql = "SELECT * FROM v_utilisateur_administrateur WHERE email=? OR nom=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, valeur);
                stmt.setString(2, valeur);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    model = new Utilisateur();
                    model.setId(rs.getInt("id"));
                    model.setNom(rs.getString("nom"));
                    model.setEmail(rs.getString("email"));
                    model.setMotDePasse(rs.getString("mot_de_passe"));
                    model.setAdministrateur(rs.getBoolean("administrateur"));
                    return model;
                } 
            } 
            throw new Exception("Email inconnue");
        } catch(Exception ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }


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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    public boolean isAdministrateur() {
        return administrateur;
    }
    public void setAdministrateur(boolean administrateur) {
        this.administrateur = administrateur;
    }

    
}