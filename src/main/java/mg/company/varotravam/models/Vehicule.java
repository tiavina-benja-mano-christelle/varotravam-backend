package mg.company.varotravam.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mg.company.varotravam.utils.DBConnection;

public class Vehicule {
    // Mapped attribute
    int id;
    double kilometrage;
    int puissance;
    int place;
    int porte;
    double consommation;
    int transmissionId;
    int energieId;
    int categorieId;
    int modeleId;
    int freinageId;
    int couleurId;
    int etat;

    //Not Mapped
    int marqueId;
    String transmission;
    String energie;
    String categorie;
    String modele;
    String marque;
    String freinage;
    String couleur;
    String[] images;
    String[] equipements;

    static Vehicule resultSetToVehicule(ResultSet rs) throws SQLException {
        Vehicule model = new Vehicule();
        model.setKilometrage(rs.getDouble("kilometrage"));
        model.setPuissance(rs.getInt("puissance"));
        model.setPlace(rs.getInt("place"));
        model.setPorte(rs.getInt("porte"));
        model.setConsommation(rs.getDouble("consommation"));
        model.setEtat(rs.getInt("etat_vehicule"));
        model.setTransmission(rs.getString("transmission"));
        model.setEnergie(rs.getString("energie"));
        model.setCategorie(rs.getString("categorie"));
        model.setModele(rs.getString("modele"));
        model.setFreinage(rs.getString("freinage"));
        model.setCouleur(rs.getString("couleur"));
        model.setMarque(rs.getString("marque"));
        model.setTransmissionId(rs.getInt("transmission_id"));
        model.setEnergieId(rs.getInt("energie_id"));
        model.setCategorieId(rs.getInt("categorie_id"));
        model.setModeleId(rs.getInt("modele_id"));
        model.setFreinageId(rs.getInt("freinage_id"));
        model.setCouleurId(rs.getInt("couleur_id"));
        model.setMarqueId(rs.getInt("marque_id"));
        String images = rs.getString("images");
        String equipements = rs.getString("equipements");
        if (images != null) model.setImages(images.split(";"));
        if (equipements != null)  model.setEquipements(equipements.split(";"));
        return model;
    }

    public void save(Connection connection) throws Exception {
        boolean wasConnected = true;
        if(connection == null) {
            wasConnected = false;
            connection = DBConnection.getConnection();
        }
        String sql = "INSERT INTO \"public\".vehicule ( id, kilometrage, puissance, place, porte, consommation, transmission_id, energie_id, categorie_id, modele_id, freinage_id, couleur_id, etat) VALUES ( default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) 
        {
            stmt.setDouble(1, getKilometrage());
            stmt.setInt(2, getPuissance());
            stmt.setInt(3, getPlace());
            stmt.setInt(4, getPorte());
            stmt.setDouble(5, getConsommation());
            stmt.setInt(6, getTransmissionId());
            stmt.setInt(7, getEnergieId());
            stmt.setInt(8, getCategorieId());
            stmt.setInt(9, getModeleId());
            stmt.setInt(10, getFreinageId());
            stmt.setInt(11, getCouleurId());
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
    public double getKilometrage() {
        return kilometrage;
    }
    public void setKilometrage(double kilometrage) {
        this.kilometrage = kilometrage;
    }
    public int getPuissance() {
        return puissance;
    }
    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }
    public int getPlace() {
        return place;
    }
    public void setPlace(int place) {
        this.place = place;
    }
    public int getPorte() {
        return porte;
    }
    public void setPorte(int porte) {
        this.porte = porte;
    }
    public double getConsommation() {
        return consommation;
    }
    public void setConsommation(double consommation) {
        this.consommation = consommation;
    }
    public int getTransmissionId() {
        return transmissionId;
    }
    public void setTransmissionId(int transmissionId) {
        this.transmissionId = transmissionId;
    }
    public int getEnergieId() {
        return energieId;
    }
    public void setEnergieId(int energieId) {
        this.energieId = energieId;
    }
    public int getCategorieId() {
        return categorieId;
    }
    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }
    public int getModeleId() {
        return modeleId;
    }
    public void setModeleId(int modeleId) {
        this.modeleId = modeleId;
    }
    public int getFreinageId() {
        return freinageId;
    }
    public void setFreinageId(int freinageId) {
        this.freinageId = freinageId;
    }
    public int getCouleurId() {
        return couleurId;
    }
    public void setCouleurId(int couleurId) {
        this.couleurId = couleurId;
    }
    public int getEtat() {
        return etat;
    }
    public void setEtat(int etat) {
        this.etat = etat;
    }
    public String getTransmission() {
        return transmission;
    }
    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
    public String getEnergie() {
        return energie;
    }
    public void setEnergie(String energie) {
        this.energie = energie;
    }
    public String getCategorie() {
        return categorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    public String getModele() {
        return modele;
    }
    public void setModele(String modele) {
        this.modele = modele;
    }
    public String getMarque() {
        return marque;
    }
    public void setMarque(String marque) {
        this.marque = marque;
    }
    public String getFreinage() {
        return freinage;
    }
    public void setFreinage(String freinage) {
        this.freinage = freinage;
    }
    public String getCouleur() {
        return couleur;
    }
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String[] getEquipements() {
        return equipements;
    }

    public void setEquipements(String[] equipements) {
        this.equipements = equipements;
    }

    public int getMarqueId() {
        return marqueId;
    }

    public void setMarqueId(int marqueId) {
        this.marqueId = marqueId;
    }

}
