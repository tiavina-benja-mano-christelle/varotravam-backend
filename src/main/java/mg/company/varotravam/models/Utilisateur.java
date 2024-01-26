package mg.company.varotravam.models;

import java.sql.*;

import mg.company.varotravam.utils.DBConnection;

public class Utilisateur {
    int id;
    String nom;
    String email;
    String motDePasse;
    boolean administrateur;
    Date date_inscription;

    public static Utilisateur resultSetToUtilisateur(ResultSet rs) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(rs.getString("utilisateur_nom"));
        utilisateur.setEmail(rs.getString("utilisateur_email"));
        utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
        return utilisateur;
    }

    /**
     * DAO: sauvegarde l'utilisateur
     * @param connection
     */
    public void save(Connection connection) throws SQLException {
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "INSERT INTO utilisateur (id, nom, email, mot_de_passe, administrateur) VALUES (default, ?, ?, ?, default) RETURNING id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setString(1, this.getNom());
            stmt.setString(2, this.getEmail());
            stmt.setString(3, this.getMotDePasse());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                this.setId(rs.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

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
    
    /**
     * Récupère le nombre d'utilisateur inscrit dans le site
     * @param connection
     * @return
     * @throws SQLException
     */
    public static int getInscrit(Connection connection) throws SQLException{
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        int ttl = 0;
        try {
            String sql = "SELECT count(*) AS inscrit from v_utilisateur_client";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                   ttl = rs.getInt("inscrit");
                } else {
                    throw new SQLException("Nombre d'inscrit introuvable");
                }
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return ttl;
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