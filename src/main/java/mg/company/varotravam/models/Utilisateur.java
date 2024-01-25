package mg.company.varotravam.models;

import java.sql.*;

import mg.company.varotravam.utils.DBConnection;

public class Utilisateur {
    int id;
    String nom;
    String email;
    String motDePasse;
    boolean administrateur;

    //changement
    Date date_inscription;

    /**
     * Vérifie si l'identifiant correspond à un utilisateur
     * @param id
     * @param connection
     * @return
     * @throws Exception
     */
    public static Utilisateur verifierUtilisateur(int id, Connection connection) throws Exception {
        Utilisateur utilisateur = findUserById(id, connection);
        if (utilisateur.isAdministrateur()) throw new Exception("Utilisateur non reconnue");
        return utilisateur;
    }

    /**
     * Vérifie si l'identifiant correspond à un administrateur
     * @param id
     * @param connection
     * @return
     * @throws Exception
     */
    public static Utilisateur verifierAdministrateur(int id, Connection connection) throws Exception {
        Utilisateur utilisateur = findUserById(id, connection);
        if (!utilisateur.isAdministrateur()) throw new Exception("Administrateur non reconnue");
        return utilisateur;
    }

    /**
     * Récupère l'utilisateur par son identifiant
     * @param id
     * @param connection
     * @return
     * @throws SQLException
     */
    public static Utilisateur findUserById(int id, Connection connection) throws Exception {
        Utilisateur model = null;
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        try {
            String sql = "SELECT * FROM v_utilisateur_client WHERE id=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    model = new Utilisateur();
                    model.setId(rs.getInt("id"));
                    model.setNom(rs.getString("nom"));
                    model.setEmail(rs.getString("email"));
                    model.setMotDePasse(rs.getString("mot_de_passe"));
                    model.setAdministrateur(rs.getBoolean("administrateur"));
                } else {
                    throw new Exception("Utilisateur inconnue");
                }
            } 
        } catch(Exception ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return new Utilisateur();
    }

    /**
     * Vérifie si le nom et le mot de passe envoyé est administrateur
     * @param nom
     * @param password
     * @return
     * @throws Exception
     */
    public static Utilisateur verifierAdministrateur(String nom, String password) throws Exception {
        try {
            Utilisateur utilisateur = chercherAdministrateurParNomOuEmail(nom, null);
            utilisateur.verifier(password);
            return utilisateur;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Vérifie si le nom et le mot de passe correspond à un utilisateur
     * @param nom
     * @param password
     * @return
     * @throws Exception
     */
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
            String sql = "SELECT * FROM v_utilisateur_admin WHERE email=? OR nom=?";
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
    //GRAPHE : inscrit ttl
    public static int getTtlInscrit(Connection connection) throws Exception{
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        int ttl = 0;
        try {
            String sql = "SELECT count(*) AS inscrit from utilisateur;";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                   ttl = rs.getInt("insccrit");
                    return ttl;
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
    //date_inscription
    public Date getDate_inscription() {return date_inscription;}
    public void setDate_inscription(Date date_inscription) {this.date_inscription = date_inscription;}
}