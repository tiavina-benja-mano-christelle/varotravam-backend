package mg.company.varotravam.models;

import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mg.company.varotravam.utils.DBConnection;

public class Annonce {
    int id;
    double prixInitial;
    Date datePublication;
    Date dateFermeture;
    String description;
    int vehiculeId;
    int utilisateurId;
    int etat;
    Vehicule vehicule;

    /**
     * Ajoute l'annonce au favori de l'utilisateur
     * @param utilisateurId l'identifiant de l'utilisateur qui met en favori
     * @param annonceId l'identifiant de l'annonce a mettre en favorie
     * @param connection
     */
    public static void addToFavorite(int utilisateurId, int annonceId, Connection connection) {
        //TODO: ajouter dans les favories
    }

    /**
     * Recherche les annonces en attente de validation
     * @param connection
     * @return
     * @throws Exception
     */
    public List<Annonce> findWaiting(Connection connection) throws Exception {
        //TODO: rechercher les annonces en attente de validation
        return new ArrayList<>();
    }

    /**
     * Recherche les annonces disponibles en ce moment
     * @param connection
     * @return
     * @throws Exception
     */
    public List<Annonce> findDispo(Connection connection) throws Exception {
        List<Annonce> models = new ArrayList<>();
        boolean wasConnected = true;
        
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        
        String sql = "SELECT * FROM v_annonce_dispo_detailled";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Annonce model = new Annonce();
                    model.setId(rs.getInt("annonce_id"));
                    model.setPrixInitial(rs.getDouble("prix_initial"));
                    model.setDatePublication(rs.getDate("date_publication"));
                    model.setDateFermeture(rs.getDate("date_fermeture"));
                    model.setDescription(rs.getString("description"));
                    model.setVehiculeId(rs.getInt("vehicule_id"));
                    model.setEtat(rs.getInt("etat_annonce"));
                    model.setVehicule(Vehicule.resultSetToVehicule(rs));
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

    /**
     * Recherche les annonces favorites de l'utilisateur
     * @param connection
     * @param utilisateurId l'utilisateur en question
     * @return
     * @throws Exception
     */
    public List<Annonce> findFavori(int utilisateurId, Connection connection) throws Exception {
        List<Annonce> models = new ArrayList<>();
        boolean wasConnected = true;
        
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        
        String sql = "SELECT * FROM v_annonce_favorites WHERE utilisateur_favori_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, utilisateurId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Annonce model = new Annonce();
                model.setId(rs.getInt("annonce_id"));
                model.setPrixInitial(rs.getDouble("prix_initial"));
                model.setDatePublication(rs.getDate("date_publication"));
                model.setDateFermeture(rs.getDate("date_fermeture"));
                model.setDescription(rs.getString("description"));
                model.setVehiculeId(rs.getInt("vehicule_id"));
                model.setEtat(rs.getInt("etat_annonce"));
                model.setVehicule(Vehicule.resultSetToVehicule(rs));
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

    /**
     * Enregistre l'annonce avec l'etat par défaut
     * @param connection
     * @throws Exception
     */
    public void save(Connection connection) throws Exception {
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "INSERT INTO \"public\".annonce ( id, prix_initial, date_publication, date_fermeture, description, vehicule_id, utilisateur_id, etat) VALUES ( default, ?, ?, ?, ?, ?, ?, default ) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setDouble(1, getPrixInitial());
            stmt.setDate(2, getDatePublication());
            stmt.setDate(3, getDateFermeture());
            stmt.setString(4, getDescription());
            stmt.setInt(5, getVehiculeId());
            stmt.setInt(6, getUtilisateurId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                setId(rs.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
    public double getPrixInitial() {
        return prixInitial;
    }
    public void setPrixInitial(double prixInitial) {
        this.prixInitial = prixInitial;
    }
    public Date getDatePublication() {
        return datePublication;
    }
    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }
    public Date getDateFermeture() {
        return dateFermeture;
    }
    public void setDateFermeture(Date dateFermeture) {
        this.dateFermeture = dateFermeture;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getVehiculeId() {
        return vehiculeId;
    }
    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }
    public int getUtilisateurId() {
        return utilisateurId;
    }
    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
    public int getEtat() {
        return etat;
    }
    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    
}
