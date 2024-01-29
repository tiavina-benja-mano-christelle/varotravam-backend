package mg.company.varotravam.models.utils;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    private List<Integer> marques = new ArrayList<>();
    private List<Integer> categories = new ArrayList<>();
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

    
}
