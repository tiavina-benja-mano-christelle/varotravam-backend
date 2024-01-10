package mg.company.varotravam.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.utils.Bag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController extends MonController{
    
    /**
     * Récupère tous les categories de véhicules
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<Bag> recupererTout() {
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Ajouter une nouvelle categorie de véhicule
     * @param categorie
     * @return
     */
    @PostMapping("/ajouter")
    public ResponseEntity<Bag> AjouterCategorie(@RequestBody Object categorie, HttpServletRequest request) {
        try {
            if (!verifierAdministrateur(request, null)) {
                bag.setError("Non autoriser");
                return new ResponseEntity<>(bag, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(bag, HttpStatus.UNAUTHORIZED);
        }
        //TODO: implement l'ajout de categorie
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    
    

}
