package mg.company.varotravam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.models.Utilisateur;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.JWTtokens;

@RestController
@RequestMapping("/authentification")
public class UtilisateursController {

    /**
     * Controller pour inscrire un nouvel utilisateur
     * @param utilisateur
     * @return
     */
    @PostMapping("/sign-in")
    public ResponseEntity<Bag> signIn(@RequestBody Utilisateur utilisateur) {
        Bag bag = new Bag();
        try {
            utilisateur.save(null);
            String token = JWTtokens.create(utilisateur.getId(), "user");
            bag.setTokens(token);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    @GetMapping("/utilisateur")
    public ResponseEntity<Bag> testUtilisateur(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            int userId = JWTtokens.checkWithRole(request, "user");
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/administrateur")
    public ResponseEntity<Bag> testAdministrateur(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        }
    }
    
    /**
     * se connecte en tant qu'utilisateur
     * renvoie un token si la connection est réussi
     * @param utilisateur
     * @return
     */
    @PostMapping("/utilisateur")
    public ResponseEntity<Bag> connectionUtilisateur(@RequestBody Utilisateur utilisateur) {
        Bag bag = new Bag();
        try {
            Utilisateur actuelUtilisateur = Utilisateur.verifierUtilisateur(utilisateur.getNom(), utilisateur.getMotDePasse());
            String tokens = JWTtokens.create(actuelUtilisateur.getId(), "user");
            bag.setTokens(tokens);
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * se connecte en tant qu'administrateur
     * renvoie un token si la connection est réussi
     * 200: connection réussi
     * 401: erreur de connection
     * @param utilisateur
     * @return
     */
    @PostMapping("/administrateur")
    public ResponseEntity<Bag> connectionAdministrateur(@RequestBody Utilisateur utilisateur) {
        Bag bag = new Bag();
        try {
            Utilisateur actuelUtilisateur = Utilisateur.verifierAdministrateur(utilisateur.getNom(), utilisateur.getMotDePasse());
            String tokens = JWTtokens.create(actuelUtilisateur.getId(), "admin");
            bag.setTokens(tokens);
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        }
    }
}
