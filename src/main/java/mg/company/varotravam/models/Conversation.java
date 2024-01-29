package mg.company.varotravam.models;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private int annonceId;
    private int acheteurId;
    private int vendeurId;
    private String acheteurNom;
    private String vendeurNom;
    private List<Message> messages;
    private String message;


    private static final String DATABASE_NAME = "varotravam";
    private static final String COLLECTION_NAME = "conversation";

    public static void getCollection() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://monorail.proxy.rlwy.net:51470")) {
            // Sélectionner une base de données
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Sélectionner une collection
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        }
    }

    public static void create(MongoCollection<Document> collection) {
        List<Document> messages = new ArrayList<>();
        messages.add(new Document("acheteur_message", "").append("vendeur_message", ""));

        Document annonceDocument = new Document("annonce_id", 0)
                .append("acheteur_id", 0)
                .append("vendeur_id", 0)
                .append("acheteur_nom", "")
                .append("vendeur_nom", "")
                .append("messages", messages);

        collection.insertOne(annonceDocument);

        System.out.println("Annonce créée avec succès.");
    }

    // public static void read(MongoCollection<Document> collection, int annonceId) {
    //     System.out.println("Lecture de l'annonce avec critères :");
    //     collection.find(Filters.and(
    //             Filters.eq("annonce_id", annonceId)
    //     )).forEach((Document documentResult) -> System.out.println(documentResult.toJson()));
    // }

    // public static void read(MongoCollection<Document> collection, int annonceId, int acheteurId) {
    //     System.out.println("Lecture de l'annonce avec critères :");
    //     collection.find(Filters.and(
    //             Filters.eq("annonce_id", annonceId),
    //             Filters.eq("acheteur_id", acheteurId)
    //     )).forEach((Document documentResult) -> System.out.println(documentResult.toJson()));
    // }

    public void addAcheteurMessage(MongoCollection<Document> collection) {
        System.out.println("Ajout d'un message à l'annonce :");

        collection.updateOne(
            Filters.and(
                Filters.eq("annonce_id", this.getAnnonceId()),
                Filters.eq("acheteur_id", this.getAcheteurId())
            ),
            Updates.push("messages", new Document("acheteur_message", this.getMessage()).append("vendeur_message", ""))
        );

        System.out.println("Message ajouté avec succès.");
    }

    public void addVendeurMessage(MongoCollection<Document> collection) {
        System.out.println("Ajout d'un message à l'annonce :");

        collection.updateOne(
            Filters.and(
                Filters.eq("annonce_id", this.getAnnonceId()),
                Filters.eq("acheteur_id", this.getAcheteurId())
            ),
            Updates.push("messages", new Document("acheteur_message", "").append("vendeur_message", this.getMessage()))
        );

        System.out.println("Message ajouté avec succès.");
    }

    public int getAnnonceId() {
        return annonceId;
    }

    public void setAnnonceId(int annonceId) {
        this.annonceId = annonceId;
    }

    public int getAcheteurId() {
        return acheteurId;
    }

    public void setAcheteurId(int acheteurId) {
        this.acheteurId = acheteurId;
    }

    public int getVendeurId() {
        return vendeurId;
    }

    public void setVendeurId(int vendeurId) {
        this.vendeurId = vendeurId;
    }

    public String getAcheteurNom() {
        return acheteurNom;
    }

    public void setAcheteurNom(String acheteurNom) {
        this.acheteurNom = acheteurNom;
    }

    public String getVendeurNom() {
        return vendeurNom;
    }

    public void setVendeurNom(String vendeurNom) {
        this.vendeurNom = vendeurNom;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static String getCollectionName() {
        return COLLECTION_NAME;
    }

    



    
}