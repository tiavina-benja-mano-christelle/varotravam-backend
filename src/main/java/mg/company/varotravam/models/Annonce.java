package mg.company.varotravam.models;

import mg.company.varotravam.utils.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Annonce {
    int id;
    int modele_id;
    int energie_id;
    int vitesse_id;
    int annee;
    int kilometrage;
    double prix_initial;
    int etat;
    int categorie_id;
    Date date_publication;
    Date date_fermeture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModele_id() {
        return modele_id;
    }

    public void setModele_id(int modele_id) {
        this.modele_id = modele_id;
    }

    public int getEnergie_id() {
        return energie_id;
    }

    public void setEnergie_id(int energie_id) {
        this.energie_id = energie_id;
    }

    public int getVitesse_id() {
        return vitesse_id;
    }

    public void setVitesse_id(int vitesse_id) {
        this.vitesse_id = vitesse_id;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }

    public double getPrix_initial() {
        return prix_initial;
    }

    public void setPrix_initial(double prix_initial) {
        this.prix_initial = prix_initial;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(int categorie_id) {
        this.categorie_id = categorie_id;
    }

    public Date getDate_publication() {
        return date_publication;
    }

    public void setDate_publication(Date date_publication) {
        this.date_publication = date_publication;
    }

    public Date getDate_fermeture() {
        return date_fermeture;
    }

    public void setDate_fermeture(Date date_fermeture) {
        this.date_fermeture = date_fermeture;
    }

    public Annonce(int id, int modele_id, int energie_id, int vitesse_id, int annee, int kilometrage, double prix_initial, int etat, int categorie_id, Date date_publication, Date date_fermeture) {
        this.id = id;
        this.modele_id = modele_id;
        this.energie_id = energie_id;
        this.vitesse_id = vitesse_id;
        this.annee = annee;
        this.kilometrage = kilometrage;
        this.prix_initial = prix_initial;
        this.etat = etat;
        this.categorie_id = categorie_id;
        this.date_publication = date_publication;
        this.date_fermeture = date_fermeture;
    }

    public Annonce() {
    }

    public Vector<Annonce> getAllBoiteVitesse(Connection connection) throws ClassNotFoundException, SQLException{
        Vector<Annonce> annonces = new Vector<>();
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        String sql = "select * from annonce";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Annonce annonce = new Annonce();
                annonce.setId(resultSet.getInt("id"));
                annonce.setModele_id(resultSet.getInt("modele_id"));                
                annonce.setEnergie_id(resultSet.getInt("energie_id"));
                annonce.setVitesse_id(resultSet.getInt("vitesse_id"));
                annonce.setAnnee(resultSet.getInt("annee"));
                annonce.setKilometrage(resultSet.getInt("kilometrage"));
                annonce.setPrix_initial(resultSet.getDouble("prix_initial"));
                annonce.setEtat(resultSet.getInt("etat"));    
                annonce.setCategorie_id(resultSet.getInt("categorie_id"));
                annonce.setDate_publication(resultSet.getDate("date_publication"));
                annonce.setDate_fermeture(resultSet.getDate("date_fermeture"));
                annonces.add(annonce);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
        return annonces;
    }
    
    public Annonce findById (Connection connection) throws Exception{
        Annonce model = null;
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }

        try {
            String sql = "SELECT * FROM annonce WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, this.getId());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    model = new Annonce();
                    model.setId(rs.getInt("id"));
                    model.setModele_id(rs.getInt("modele_id"));                
                    model.setEnergie_id(rs.getInt("energie_id"));
                    model.setVitesse_id(rs.getInt("vitesse_id"));
                    model.setAnnee(rs.getInt("annee"));
                    model.setKilometrage(rs.getInt("kilometrage"));
                    model.setPrix_initial(rs.getDouble("prix_initial"));
                    model.setEtat(rs.getInt("etat"));    
                    model.setCategorie_id(rs.getInt("categorie_id"));
                    model.setDate_publication(rs.getDate("date_publication"));
                    model.setDate_fermeture(rs.getDate("date_fermeture"));
                    return model;
                } 
            }
            throw new Exception("Error");
        } catch(Exception ex) {
            throw ex;
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }

    public void saveAnnonce(Connection connection) throws SQLException, ClassNotFoundException {
        boolean wasConnected = true;

        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "insert into annonce(id, modele_id, energie_id, vitesse_id, annee, kilometrage, prix_initial, categorie_id, date_publication, date_fermeture) values(default, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, this.getModele_id());            
            statement.setInt(2, this.getEnergie_id());
            statement.setInt(3, this.getVitesse_id());
            statement.setInt(4, this.getAnnee());
            statement.setInt(5, this.getKilometrage());
            statement.setDouble(6, this.getPrix_initial());
            statement.setInt(7, this.getCategorie_id());
            statement.setDate(8, this.getDate_publication());
            statement.setDate(9, this.getDate_fermeture());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (!wasConnected) {
                connection.close();
            }
        }
    }
}