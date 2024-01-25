package mg.company.varotravam.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mg.company.varotravam.utils.DBConnection;

public class Utilisateur {
    int id;
    String nom;
    String email;
    String motDePasse;
    boolean administrateur;

    //changement
    Date date_inscription;

    //GRAPHE
    int periode ,temp , nombre;
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
    //GRAPHE
    //month
    public List<Utilisateur> getUserByMonth(Connection connection) throws Exception {
        List<Utilisateur> models = new ArrayList<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "SELECT gs.mois, COALESCE(COUNT(u.id), 0) AS nombre_inscrits\n" +
                "FROM generate_series(1, 12) gs(mois)\n" +
                "LEFT JOIN utilisateur u ON EXTRACT(MONTH FROM u.date_inscription) = gs.mois\n" +
                "GROUP BY gs.mois  ORDER BY gs.mois;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Utilisateur model = new Utilisateur();
                model.setPeriode(rs.getInt("mois"));
                model.setNombre(rs.getInt("nombre_inscrits"));
                models.add(model);
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

    //year
    public List<Utilisateur> getUserByYear(Connection connection) throws Exception {
        List<Utilisateur> models = new ArrayList<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "SELECT EXTRACT(YEAR FROM date_inscription) AS annee, COUNT(*) AS nombre_inscrits\n" +
                "FROM utilisateur\n" +
                "GROUP BY EXTRACT(YEAR FROM date_inscription)  ORDER BY annee";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Utilisateur model = new Utilisateur();
                model.setPeriode(rs.getInt("annee"));
                model.setNombre(rs.getInt("nombre_inscrits"));
                models.add(model);
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
    //both
    public List<Utilisateur> getUserBoth(Connection connection) throws Exception {
        List<Utilisateur> models = new ArrayList<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "SELECT EXTRACT(YEAR FROM date_inscription) AS annee, EXTRACT(MONTH FROM date_inscription) AS mois, COUNT(*) AS nombre_inscrits FROM utilisateur\n" +
                "GROUP BY EXTRACT(YEAR FROM date_inscription), EXTRACT(MONTH FROM date_inscription)  ORDER BY annee, mois;\n";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Utilisateur model = new Utilisateur();
                model.setPeriode(rs.getInt("annee"));
                model.setTemp(rs.getInt("mois"));
                model.setNombre(rs.getInt("nombre_inscrits"));
                models.add(model);
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

    //GRAPHE :
    public int getPeriode() {return periode;}
    public void setPeriode(int periode) {this.periode = periode;}
    public int getNombre() {return nombre;}
    public void setNombre(int nombre){ this.nombre = nombre;}
    public int getTemp() {return temp;}
    public void setTemp(int temp) {this.temp = temp;}
}