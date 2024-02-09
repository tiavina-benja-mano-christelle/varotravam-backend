package mg.company.varotravam.controllers;

import java.util.List;
import java.util.Vector;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
// import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.exceptions.NotAuthorizedException;
import mg.company.varotravam.models.Annonce;

// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
// import com.mongodb.client.MongoCollection;
// import com.mongodb.client.MongoDatabase;

import mg.company.varotravam.models.Conversation;
import mg.company.varotravam.models.Message;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.JWTtokens;

@RestController
@RequestMapping("/api/v1/messages")
public class MessagesController {
    // private static final String CONNECTION_STRING = "monorail.proxy.rlwy.net:51470";
    // private static final String DATABASE_NAME = "varotravam";
    // private static final String COLLECTION_NAME = "conversation";

    // // @GetMapping
    // // public String read(@RequestParam int annonceId) {
    // //     try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
    // //         MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
    // //         MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
    // //         Conversation.read(collection, annonceId, 1);
    // //     }
    // //     return null;
    // // }

    // // @GetMapping("/{acheteurId}")
    // // public String read(@RequestParam int annonceId, @PathVariable int acheteurId) {
    // //     try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
    // //         MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
    // //         MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
    // //         Conversation.read(collection, 1, 1);
    // //     }
    // //     return null;
    // // }

    // @PostMapping("/acheteur")
    // public void addAcheteurMessage(@RequestBody Conversation conversation) {
    //     try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
    //         MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
    //         MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
    //         conversation.addAcheteurMessage(collection);
    //     }
    // }

    // @PostMapping("/vendeur")
    // public void addVendeurMessage(@RequestBody Conversation conversation) {
    //     try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
    //         MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
    //         MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
    //         conversation.addVendeurMessage(collection);
    //     }
    // }

    @GetMapping("/all-message")
    public ResponseEntity<Bag> enAttente(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            int id_utilisateur = JWTtokens.checkWithRole(request, "admin");
            Vector<Message> annonces = new Message().getMessageByUtilisateur(id_utilisateur, null);
            bag.setData(annonces); 
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(bag, HttpStatus.OK);
    }

}