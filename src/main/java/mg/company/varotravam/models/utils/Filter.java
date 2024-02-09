package mg.company.varotravam.models.utils;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    private List<Integer> marques = new ArrayList<>();
    private List<Integer> modeles = new ArrayList<>();
    private List<Integer> energies = new ArrayList<>();
    private List<Integer> categories = new ArrayList<>();
    private List<Integer> transmissions = new ArrayList<>();
    private List<Integer> couleurs = new ArrayList<>();
    private int prixMin = 0;
    private int prixMax = Integer.MAX_VALUE;
    private int transmission = 0;
    private int annee = 0;

    public List<Integer> getMarques() {
        return marques;
    }
    public void setMarques(List<Integer> marques) {
        this.marques = marques;
    }
    public List<Integer> getCategories() {
        return categories;
    }
    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }
    public int getPrixMin() {
        return prixMin;
    }
    public void setPrixMin(int prixMin) {
        this.prixMin = prixMin;
    }
    public int getPrixMax() {
        return prixMax;
    }
    public void setPrixMax(int prixMax) {
        this.prixMax = prixMax;
    }
    public int getTransmission() {
        return transmission;
    }
    public void setTransmission(int transmission) {
        this.transmission = transmission;
    }
    public int getAnnee() {
        return annee;
    }
    public void setAnnee(int annee) {
        this.annee = annee;
    }
    public List<Integer> getTransmissions() {
        return transmissions;
    }
    public void setTransmissions(List<Integer> transmissions) {
        this.transmissions = transmissions;
    }
    public List<Integer> getModeles() {
        return modeles;
    }
    public void setModeles(List<Integer> modeles) {
        this.modeles = modeles;
    }
    public List<Integer> getEnergies() {
        return energies;
    }
    public void setEnergies(List<Integer> energies) {
        this.energies = energies;
    }
    public List<Integer> getCouleurs() {
        return couleurs;
    }
    public void setCouleurs(List<Integer> couleurs) {
        this.couleurs = couleurs;
    }

    
}
