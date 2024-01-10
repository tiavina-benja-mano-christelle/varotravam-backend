package mg.company.varotravam.models;

public class Model {
    int id,marque_id;
    String nom;
//--GETTERS && SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarque_id() {
        return marque_id;
    }

    public void setMarque_id(int marque_id) {
        this.marque_id = marque_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
//--CONSTRUCTORS
    public Model() {}
    public Model( String nom,int marque_id) {
        this.setMarque_id(marque_id);
        this.setNom(nom);
    }
    public Model(int id, String nom, int marque_id) {
        this.setId(id);
        this.setMarque_id(marque_id);
        this.setNom(nom);
    }
}
