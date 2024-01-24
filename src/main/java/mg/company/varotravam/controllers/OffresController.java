package mg.company.varotravam.controllers;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.exceptions.NotAuthorizedException;
import mg.company.varotravam.models.Offre;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.JWTtokens;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/offres")
public class OffresController {

    /**
     * Montre les offres envoyés et encore actif
     * @return
     */
    @GetMapping("/envoye")
    public ResponseEntity<Bag> envoye(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            int utilisateurId = JWTtokens.checkWithRole(request, "user");
            bag.setData(Offre.findSended(utilisateurId, null));
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }
    

    /**
     * Montre la liste des offres reçus par l'utilisateur actuel
     * @return
     */
    @GetMapping("/recu")
    public ResponseEntity<Bag> recus(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            int utilisateurId = JWTtokens.checkWithRole(request, "user");
            bag.setData(Offre.findReceived(utilisateurId, null));
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Montre la liste des offres reçus par l'utilisateur actuel pour une annonce
     * @return
     */
    @GetMapping("/recu/{annonceId}")
    public ResponseEntity<Bag> recus(@PathVariable int annonceId, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            int utilisateurId = JWTtokens.checkWithRole(request, "user");
            bag.setData(Offre.findReceivedByAnnonce(annonceId, utilisateurId, null));
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    

    /**
     * Montre les détails de l'offre
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bag> details(@PathVariable int id, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "user");
            bag.setData(Offre.findById(id, null));
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    

    /**
     * refuse ou annule l'offre en question
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Bag> refused(@PathVariable int id, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "user");
            new Offre().refuse(id, null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }

    /**
     * Valide l'offre en question
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    public ResponseEntity<Bag> validate(@PathVariable int id, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "user");
            new Offre().accept(id, null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }
    
    
    /**
     * Controller pour la création d'une offre
     * @param offre
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<Bag> create(@RequestBody Offre offre ,HttpServletRequest request ) {
        Bag bag = new Bag();
        try {
            offre.save(null);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Envoie une contre-offre à une offre
     * @param offre
     * @return
     */
    @PutMapping
    public ResponseEntity<Bag> contre(@RequestBody Offre offre) {
        try {
            offre.contre(null);
        } catch (Exception e) {
            return new ResponseEntity<Bag>(new Bag(), HttpStatus.INTERNAL_SERVER_ERROR);
        }        
        return new ResponseEntity<Bag>(new Bag(), HttpStatus.OK);
    }
    

}
