package mg.company.varotravam.model;

/**
 * Marque
 */
public class Marque {
    int id;
    String  nom;
//--GETTERS && SETTERS
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
//--CONSTRUCTORS
    public Marque() {}
    public Marque(String nom) {
        this.setNom(nom);
    }
    public Marque(int id, String nom) {
        this.setId(id);
        this.setNom(nom);
    }
}