package mg.company.varotravam.controllers;

import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import mg.company.varotravam.models.Conversation;

@RestController
@RequestMapping("/api/v1/messages")
public class MessagesController {
    private static final String DATABASE_NAME = "varotravam";
    private static final String COLLECTION_NAME = "conversation";

    // @GetMapping
    // public String read(@RequestParam int annonceId) {
    //     try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
    //         MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
    //         MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
    //         Conversation.read(collection, annonceId, 1);
    //     }
    //     return null;
    // }

    // @GetMapping("/{acheteurId}")
    // public String read(@RequestParam int annonceId, @PathVariable int acheteurId) {
    //     try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
    //         MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
    //         MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
    //         Conversation.read(collection, 1, 1);
    //     }
    //     return null;
    // }

    @PostMapping("/acheteur")
    public void addAcheteurMessage(@RequestBody Conversation conversation) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            conversation.addAcheteurMessage(collection);
        }
    }

    @PostMapping("/vendeur")
    public void addVendeurMessage(@RequestBody Conversation conversation) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            conversation.addVendeurMessage(collection);
        }
    }

}