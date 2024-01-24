package mg.company.varotravam.models.viewmodel;

import mg.company.varotravam.models.utils.Graphe;

public class StatViewModel {
    double commision;
    int nbAnnonceDispo;
    int nbInscritTotal;
    Graphe nouvelleAnnonce;
    Graphe annonceVendue;
    Graphe modeles;
    Graphe marques;
    Graphe chiffreAffaire;
    Graphe meilleurVendeur;
    int chiffreAffaireTotal;

    public double getCommision() {
        return commision;
    }
    public void setCommision(double commision) {
        this.commision = commision;
    }
    public int getNbAnnonceDispo() {
        return nbAnnonceDispo;
    }
    public void setNbAnnonceDispo(int nbAnnonceDispo) {
        this.nbAnnonceDispo = nbAnnonceDispo;
    }
    public int getNbInscritTotal() {
        return nbInscritTotal;
    }
    public void setNbInscritTotal(int nbInscritTotal) {
        this.nbInscritTotal = nbInscritTotal;
    }
    public Graphe getNouvelleAnnonce() {
        return nouvelleAnnonce;
    }
    public void setNouvelleAnnonce(Graphe nouvelleAnnonce) {
        this.nouvelleAnnonce = nouvelleAnnonce;
    }
    public Graphe getAnnonceVendue() {
        return annonceVendue;
    }
    public void setAnnonceVendue(Graphe annonceVendue) {
        this.annonceVendue = annonceVendue;
    }
    public Graphe getModeles() {
        return modeles;
    }
    public void setModeles(Graphe modeles) {
        this.modeles = modeles;
    }
    public Graphe getMarques() {
        return marques;
    }
    public void setMarques(Graphe marques) {
        this.marques = marques;
    }
    public Graphe getChiffreAffaire() {
        return chiffreAffaire;
    }
    public void setChiffreAffaire(Graphe chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }
    public Graphe getMeilleurVendeur() {
        return meilleurVendeur;
    }
    public void setMeilleurVendeur(Graphe meilleurVendeur) {
        this.meilleurVendeur = meilleurVendeur;
    }
    public int getChiffreAffaireTotal() {
        return chiffreAffaireTotal;
    }
    public void setChiffreAffaireTotal(int chiffreAffaireTotal) {
        this.chiffreAffaireTotal = chiffreAffaireTotal;
    }


    
}
