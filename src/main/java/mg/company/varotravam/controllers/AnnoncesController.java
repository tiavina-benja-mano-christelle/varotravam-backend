package mg.company.varotravam.controllers;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import mg.company.varotravam.exceptions.NotAuthorizedException;
import mg.company.varotravam.models.Annonce;
import mg.company.varotravam.models.Favori;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.DBConnection;
import mg.company.varotravam.utils.JWTtokens;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/api/v1/annonces")
@CrossOrigin
public class AnnoncesController {

    @GetMapping("/en-attente")
    public ResponseEntity<Bag> enAttente(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            List<Annonce> annonces = new Annonce().findWaiting(null);
            bag.setData(annonces); 
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(bag, HttpStatus.OK);
    }
    
     
    /**
     * Refuse de l'annonce
     * @param annonce
     * @param request
     * @return
     */
    @PutMapping("/refuse")
    public ResponseEntity<Bag> refuse(@RequestBody Annonce annonce, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            annonce.refuse(null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Valide de l'annonce
     * @param annonce
     * @param request
     * @return
     */
    @PutMapping("/validate")
    public ResponseEntity<Bag> validate(@RequestBody Annonce annonce, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            annonce.validate(null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Change le status de l'annonce
     * @param annonce
     * @param request
     * @return
     */
    @PutMapping
    public ResponseEntity<Bag> changeStatus(@RequestBody Annonce annonce, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "user");
            annonce.changeStatus(null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Listes des annonces envoyées par l'utilisateur connecté
     * @param request
     * @return
     */
    @GetMapping("/moi")
    public ResponseEntity<Bag> mesAnnonces(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            int utilisateurId = JWTtokens.checkWithRole(request, "user");
            List<Annonce> mesAnnonces = new Annonce().findSended(utilisateurId, null);
            bag.setData(mesAnnonces);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Crée une nouvelle annonce
     * @param annonce
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<Bag> create(@RequestBody Annonce annonce, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            int utilisateurID = JWTtokens.checkWithRole(request, "user");
            annonce.setUtilisateurId(utilisateurID);
            annonce.create(null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Récupère toutes les annonces actifs
     * @param param
     * @return
     */
    @GetMapping
    public ResponseEntity<Bag> annonceActif() {
        Bag bag = new Bag();
        try {
            bag.setData(new Annonce().findDispo(null));
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Récupère toutes les annonces actifs avec un filtre
     * @param param
     * @return
     */
    @PostMapping("/filtrer")
    public ResponseEntity<Bag> annonceActifFiltrer() {
        Bag bag = new Bag();
        //TODO: implementer la récuperation des annonces disponible avec les filtres
        return new ResponseEntity<>(bag, HttpStatus.OK);
    }

    /**
     * Récupère les détails de l'annonce par son identifiant
     * @param annonceId
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bag> annonceDetail(@PathVariable int id) {
        Bag bag = new Bag();
        try {
            bag.setData(new Annonce().findById(id, null));
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    

    /**
     * Récupère toutes les annonces favorites de l'utilisateur
     * @return
     */
    @GetMapping("/favorites")
    public ResponseEntity<Bag> annonceFavorites(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            int subject = JWTtokens.checkWithRole(request, "user");
            bag.setData(new Annonce().findFavori(subject, null));
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Ajoute l'annonce en favori de l'utilisateur
     * @param annonce
     * @return
     */
    @PostMapping("/favorites")
    public ResponseEntity<Bag> ajouterAuxFavori(@RequestBody int idAnnonce, HttpServletRequest request) {
        Bag bag = new Bag();
        int subject = 0;
        try {
            subject = JWTtokens.checkWithRole(request, "user");
            Favori favori = new Favori(subject, idAnnonce);
            favori.save(null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }

    /**
     * Enlever l'annonce en favori de l'utilisateur
     * @param annonce
     * @return
     */
    @DeleteMapping("/favorites")
    public ResponseEntity<Bag> enleverDesFavori(@RequestBody int idAnnonce, HttpServletRequest request) {
        Bag bag = new Bag();
        int subject = 0;
        try {
            subject = JWTtokens.checkWithRole(request, "user");
            Favori favori = new Favori(subject, idAnnonce);
            Connection connection = DBConnection.getConnection();
            favori.find(connection);
            favori.delete(connection);
            connection.close();
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }
}
