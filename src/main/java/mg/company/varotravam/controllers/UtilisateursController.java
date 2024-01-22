package mg.company.varotravam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.models.User;
import mg.company.varotravam.models.Utilisateur;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.JWTtokens;

@RestController
@RequestMapping("/authentification")
public class UtilisateursController extends MonController{

    @GetMapping("/utilisateur")
    public ResponseEntity<Bag> testUtilisateur(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        try {
            int userId = JWTtokens.checkBearer(token);
            Utilisateur.verifierUtilisateur(userId, null);
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/administrateur")
    public ResponseEntity<Bag> testAdministrateur(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        try {
            int userId = JWTtokens.checkBearer(token);
            Utilisateur.verifierAdministrateur(userId, null);
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping("/utilisateur")
    public ResponseEntity<Bag> connectionUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur actuelUtilisateur = Utilisateur.verifierUtilisateur(utilisateur.getNom(), utilisateur.getMotDePasse());
            String tokens = JWTtokens.create(actuelUtilisateur.getId());
            bag.setTokens(tokens);
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/administrateur")
    public ResponseEntity<Bag> connectionAdministrateur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur actuelUtilisateur = Utilisateur.verifierAdministrateur(utilisateur.getNom(), utilisateur.getMotDePasse());
            String tokens = JWTtokens.create(actuelUtilisateur.getId());
            bag.setTokens(tokens);
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        }
    }
}
