package mg.company.varotravam.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.company.varotravam.models.Annonce;
import mg.company.varotravam.utils.Bag;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/annonces")
@RestController
public class AnnoncesController extends MonController {

    /**
     * Récupère toutes les annonces actifs
     * @param param
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<Bag> annonceActif() {
        //TODO: implementer la récuperation des annonces disponible
        return new ResponseEntity<>(bag, HttpStatus.OK);
    }

    /**
     * Récupère toutes les annonces actifs avec un filtre
     * @param param
     * @return
     */
    @PostMapping("/filtrer")
    public ResponseEntity<Bag> annonceActifFiltrer() {
        //TODO: implementer la récuperation des annonces disponible avec les filtres
        return new ResponseEntity<>(bag, HttpStatus.OK);
    }

    /**
     * Récupère les détails de l'annonce par son identifiant
     * @param annonceId
     * @return
     */
    @GetMapping("/{annonceId}")
    public ResponseEntity<Bag> annonceDetail(@PathVariable String annonceId) {
        //TODO: implementer la récupération de l'annonce
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    

    /**
     * Récupère toutes les annonces favorites de l'utilisateur
     * @return
     */
    @GetMapping("/favorites")
    public ResponseEntity<Bag> annonceFavorites() {
        //TODO: implementer la vérification du tokens
        //TODO: implementer la récupération des annonces favorites de l'utilisateur
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Ajoute l'annonce en favori de l'utilisateur
     * @param annonce
     * @return
     */
    @PostMapping("/ajout-favori")
    public ResponseEntity<Bag> ajouterAuxFavori(@RequestParam Annonce annonce) {
        //TODO: implementer la vérification du tokens
        //TODO: implementer l'ajout de l'annonce en favori
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }

    /**
     * Ajoute l'annonce en favori de l'utilisateur
     * @param annonce
     * @return
     */
    @PostMapping("/enlevement-favori")
    public ResponseEntity<Bag> enleverDesFavori(@RequestParam Annonce annonce) {
        //TODO: implementer la vérification du tokens
        //TODO: implementer l'ajout de l'annonce en favori
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }
    
    

    


    

}
