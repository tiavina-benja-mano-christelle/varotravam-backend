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
    private final static int INDISPONIBLE = 15;
    private final static int SELLED = 20;
    private final static int DISPONIBLE = 10;
    private final static int WAITING = 5;
    private final static int REFUSED = 1;

    int id;
    double prixInitial;
    Date datePublication;
    Date dateFermeture;
    String description;
    int vehiculeId;
    int utilisateurId;
    int etat;
    String observation;
    Vehicule vehicule;
    Utilisateur proprietaire;


    private String queryFilter(int[] marques, int[] categories) {
        String sql = "SELECT * FROM v_annonce WHERE 1=1";
        if (marques.length != 0) {
            sql += " AND marque_id IN (";
            for (int i = 0; i < marques.length; i++) {
                int marque = marques[i];
                if (i+1 != marques.length) sql += ", ";
            }
        }
        return sql;
    }

    /**
     * Refuse l'annonce
     * @param id
     * @param status
     * @param connection
     * @throws SQLException
     */
    public void refuse(Connection connection) throws SQLException {
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "UPDATE annonce set etat=?, observation=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, REFUSED);
            stmt.setString(2, this.getObservation());
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
     * Valide l'annonce
     * @param id
     * @param status
     * @param connection
     * @throws SQLException
     */
    public void validate(Connection connection) throws SQLException {
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "UPDATE annonce set etat=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, DISPONIBLE);
            stmt.setInt(2, this.getId());
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
     * Crée une annonce en sauvegardant les vehicules et les equipements et les images
     * @param connection
     * @throws SQLException
     */
    public void create(Connection connection) throws SQLException {
        boolean wasConnected = false;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        try {
            connection.setAutoCommit(false);
            this.getVehicule().create(connection);
            this.setVehiculeId(this.getVehicule().getId());
            this.save(connection);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    /**
     * Change le status de l'annonce
     * @param id
     * @param status
     * @param connection
     * @throws SQLException
     */
    public void changeStatus(Connection connection) throws SQLException {
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "UPDATE annonce set etat=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setInt(1, this.getEtat());
            stmt.setInt(2, this.getId());
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
     * recherche les annonces envoyées par l'utilisateur
     * @param utilisateurId
     * @param connection
     * @return
     * @throws SQLException
     */
    public List<Annonce> findSended(int utilisateurId, Connection connection) throws SQLException{
        List<Annonce> models = new ArrayList<>();
        boolean wasConnected = true;
        
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "SELECT * FROM v_annonce WHERE utilisateur_id = ?";
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
                model.setProprietaire(Utilisateur.resultSetToUtilisateur(rs));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return models;
    }

    /**
     * Recherche de l'annonce par son identifiant
     * @param id
     * @param connection
     * @return
     * @throws SQLException
     */
    public Annonce findById(int id, Connection connection) throws SQLException {
        Annonce model = null;
        boolean wasConnected = true;
        
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "SELECT * FROM v_annonce WHERE annonce_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                model = new Annonce();
                model.setId(rs.getInt("annonce_id"));
                model.setPrixInitial(rs.getDouble("prix_initial"));
                model.setDatePublication(rs.getDate("date_publication"));
                model.setDateFermeture(rs.getDate("date_fermeture"));
                model.setDescription(rs.getString("description"));
                model.setVehiculeId(rs.getInt("vehicule_id"));
                model.setEtat(rs.getInt("etat_annonce"));
                model.setVehicule(Vehicule.resultSetToVehicule(rs));
                model.setProprietaire(Utilisateur.resultSetToUtilisateur(rs));
            } else {
                throw new SQLException("Annonce not found");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return model;
    }

    /**
     * Ajoute l'annonce au favori de l'utilisateur
     * @param utilisateurId l'identifiant de l'utilisateur qui met en favori
     * @param annonceId l'identifiant de l'annonce a mettre en favorie
     * @param connection
     * @throws SQLException
     */
    public void addToFavorite(Connection connection) throws SQLException {
        Favori favori = new Favori(this.getUtilisateurId(), this.getId());
        favori.save(connection);
    }

    /**
     * Recherche les annonces en attente de validation
     * @param connection
     * @return
     * @throws Exception
     */
    public List<Annonce> findWaiting(Connection connection) throws Exception {
        List<Annonce> models = new ArrayList<>();
        boolean wasConnected = true;
        
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "SELECT * FROM v_annonce WHERE etat_annonce=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, WAITING);
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
                    model.setProprietaire(Utilisateur.resultSetToUtilisateur(rs));
                    models.add(model);
                }   
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return models;
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
        
        String sql = "SELECT * FROM v_annonce WHERE etat_annonce = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql) ) {
            stmt.setInt(1, DISPONIBLE);
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
        } catch (SQLException ex) {
            throw ex;
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
        } catch (SQLException ex) {
            throw ex;
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
    public void save(Connection connection) throws SQLException {
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "INSERT INTO \"public\".annonce ( id, prix_initial, date_publication, date_fermeture, description, vehicule_id, utilisateur_id, etat) VALUES ( default, ?, default, ?, ?, ?, ?, default ) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setDouble(1, getPrixInitial());
            stmt.setDate(2, getDateFermeture());
            stmt.setString(3, getDescription());
            stmt.setInt(4, getVehiculeId());
            stmt.setInt(5, getUtilisateurId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                setId(rs.getInt("id"));
            }
        } catch (SQLException ex) {
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

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Utilisateur getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Utilisateur proprietaire) {
        this.proprietaire = proprietaire;
    }

       
}