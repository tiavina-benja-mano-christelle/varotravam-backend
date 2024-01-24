package mg.company.varotravam.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mg.company.varotravam.utils.DBConnection;

public class Offre {
    private final static int ACCEPTED = 20;
    private final static int REFUSED = 1;
    private final static int WAITING = 5;
    private final static int CONTRED = 10;

    int id;
    double prixPropose;
    double prixContre;
    Date dateOffre;
    Date dateContre;
    int etat;
    int utilisateurId;
    int annonceId;

    /**
     * 
     * @param id
     * @param connection
     * @return
     */
    public static Offre findById(int id, Connection connection) throws SQLException {
        Offre model = null;
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        //TODO: update the viewName
        String sql = "SELECT * FROM <viewName> WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                //TODO: setting for the result
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return model;
    }

    /**
     * Recherche les offres recus pour une annonce précise
     * @param annonceId
     * @param utilisateur
     * @param connection
     * @return
     */
    public static List<Offre> findReceivedByAnnonce(int annonceID, int utilisateurID, Connection connection)throws SQLException {
        List<Offre> models = new ArrayList<>();
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        //TODO: update the viewName
        String sql = "SELECT * FROM <viewName> WHERE utilisateur_id=? AND annonce_id=? ORDER BY etat";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, utilisateurID);
            stmt.setInt(2, annonceID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                //TODO: setting for the result
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


    /**
     * recherche les offres recu par l'utilisateur
     * @param connection
     * @return
     */
    public static List<Offre> findReceived(int utilisateurID, Connection connection) throws SQLException {
        List<Offre> models = new ArrayList<>();
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        //TODO: update the viewName
        String sql = "SELECT * FROM <viewName> WHERE utilisateur_id=? ORDER BY etat";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, utilisateurID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                //TODO: setting for the result
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

    /**
     * recherche les offres envoyé par l'utilisateur
     * @param connection
     * @return
     */
    public static List<Offre> findSended(int utilisateurID, Connection connection) throws SQLException {
        List<Offre> models = new ArrayList<>();
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        //TODO: update the viewName
        String sql = "SELECT * FROM <viewName> WHERE utilisateur_id=? ORDER BY etat";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, utilisateurID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                //TODO: setting for the result
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

    /**
     * Sauvegarde l'offre
     * @param connection
     */
    public void save(Connection connection) throws SQLException {
        boolean wasConnected = true;
        
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "INSERT INTO \"public\".offre( id, prix_propose, prix_contre, date_offre, date_contre, etat, utilisateur_id, annonce_id) VALUES ( default, ?, null, default, null, ?, ?, ? ) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setDouble(1, this.getPrixPropose());
            stmt.setInt(2, WAITING);
            stmt.setInt(3, this.getUtilisateurId());
            stmt.setInt(4, this.getAnnonceId());
            ResultSet rs = stmt.executeQuery(); {
                if(rs.next()) {
                    this.setId(rs.getInt("id"));
                }
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
     * Ajoute une contre-offre à l'offre proposé
     * @param connection
     */
    public void contre(Connection connection) throws SQLException {
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "UPDATE offre SET etat=?,prix_contre=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, CONTRED);
            stmt.setDouble(2, this.getPrixContre());
            stmt.setInt(3, this.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }    

    /**
     * Accept l'offre
     * @param connection
     */
    public void accept(int id, Connection connection) throws SQLException {
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "UPDATE offre SET etat=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, ACCEPTED);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    /**
     * refuse l'offre
     * @param connection
     */
    public void refuse(int id, Connection connection) throws SQLException {
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "UPDATE offre SET etat=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, REFUSED);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }


    public Offre() {}

    public Offre(double prixPropose, double prixContre, Date dateOffre, Date dateContre, int etat, int utilisateurId,
            int annonceId) {
        this.prixPropose = prixPropose;
        this.prixContre = prixContre;
        this.dateOffre = dateOffre;
        this.dateContre = dateContre;
        this.etat = etat;
        this.utilisateurId = utilisateurId;
        this.annonceId = annonceId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getPrixPropose() {
        return prixPropose;
    }
    public void setPrixPropose(double prixPropose) {
        this.prixPropose = prixPropose;
    }
    public double getPrixContre() {
        return prixContre;
    }
    public void setPrixContre(double prixContre) {
        this.prixContre = prixContre;
    }
    public Date getDateOffre() {
        return dateOffre;
    }
    public void setDateOffre(Date dateOffre) {
        this.dateOffre = dateOffre;
    }
    public Date getDateContre() {
        return dateContre;
    }
    public void setDateContre(Date dateContre) {
        this.dateContre = dateContre;
    }
    public int getEtat() {
        return etat;
    }
    public void setEtat(int etat) {
        this.etat = etat;
    }
    public int getUtilisateurId() {
        return utilisateurId;
    }
    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
    public int getAnnonceId() {
        return annonceId;
    }
    public void setAnnonceId(int annonceId) {
        this.annonceId = annonceId;
    }

    
}
