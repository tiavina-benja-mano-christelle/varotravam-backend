package mg.company.varotravam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.exceptions.NotAuthorizedException;
import mg.company.varotravam.models.Conversation;
import mg.company.varotravam.repositories.ConversationRepositories;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.JWTtokens;

@RestController
@RequestMapping("/api/v1/messages")
@CrossOrigin
public class MessagesController {
    
    @Autowired
    private ConversationRepositories repo; 

    @PostMapping
    public ResponseEntity<Bag> addMessage(@RequestBody Conversation conversation, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "user");
            repo.save(conversation);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        }   catch (Exception e) {
            e.printStackTrace();
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<Bag> getMessage(@PathVariable int conversationId, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "user");
            bag.setData(repo.findById(conversationId));
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        }   catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }




}