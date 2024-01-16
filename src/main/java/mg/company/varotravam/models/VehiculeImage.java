package mg.company.varotravam.models;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class VehiculeImage {
    int id;
    int vehiculeId;
    String valeur;

    /**
     * Sauvegarder l'image de la voiture
     * @param connection
     */
    public void save(Connection connection) {
        //TODO: sauvegarder l'image
    }

    /**
     * 
     * @param vehiculeId l'identifiant du vehicule
     * @param connection
     * @return
     */
    public List<VehiculeImage> findByVehiculeId(int vehiculeId, Connection connection) {
        //TODO: rechercher les images correspondantes au véhicule 
        return new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getVehiculeId() {
        return vehiculeId;
    }
    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }
    public String getValeur() {
        return valeur;
    }
    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    
}
