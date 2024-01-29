package mg.company.varotravam.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Conversation")
public class Conversation {
    @Id
    private String _id;
    private int annonceId;
    private int acheteurId;
    private int vendeurId;
    private String acheteurNom;
    private String vendeurNom;
    private ArrayList<Message> messages;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String acheteurMessage;
        private String vendeurMessage;
    }
}
