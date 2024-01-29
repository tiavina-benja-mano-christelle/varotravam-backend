package mg.company.varotravam.repositories;

 
import mg.company.varotravam.models.Conversation;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository; 
  
public interface ConversationRepositories extends MongoRepository<Conversation, Integer> { 
    Optional<Conversation> findByAnnonceIdAndAcheteurId(int annonceId, int acheteurId);
}