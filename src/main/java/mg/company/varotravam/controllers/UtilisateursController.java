package mg.company.varotravam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.company.varotravam.models.User;
import mg.company.varotravam.models.Utilisateur;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.JWTtokens;

@RestController
@RequestMapping("/authentification")
public class UtilisateursController {
    
    @PostMapping("/utilisateur")
    public ResponseEntity<Bag> connectionUtilisateur(@RequestBody Utilisateur utilisateur) {
        Bag bag = new Bag();
        try {
            Utilisateur actuelUtilisateur = Utilisateur.verifierUtilisateur(utilisateur.getNom(), utilisateur.getMotDePasse());
            String tokens = JWTtokens.create(actuelUtilisateur.getId(), 3600000);
            bag.setData(tokens);
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/administrateur")
    public ResponseEntity<Bag> connectionAdministrateur(@RequestBody Utilisateur utilisateur) {
        Bag bag = new Bag();
        try {
            Utilisateur actuelUtilisateur = Utilisateur.verifierAdministrateur(utilisateur.getNom(), utilisateur.getMotDePasse());
            String tokens = JWTtokens.create(actuelUtilisateur.getId(), 3600000);
            bag.setData(tokens);
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        }
    }
}
