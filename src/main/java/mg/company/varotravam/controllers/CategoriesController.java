package mg.company.varotravam.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.models.Categorie;
import mg.company.varotravam.utils.Bag;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController extends MonController{
    
    /**
     * Récupère tous les categories de véhicules
     * @return
     */
    @GetMapping
    public ResponseEntity<Bag> recupererTout(HttpServletRequest request) {
        try {
            bag.setData(new Categorie().getAllCategorie(null));
        } catch (ClassNotFoundException | SQLException e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Ajouter une nouvelle categorie de véhicule
     * @param categorie
     * @return
     */
    @PostMapping
    public ResponseEntity<Bag> AjouterCategorie(@RequestBody Categorie categorie, HttpServletRequest request) {
        try {
            categorie.saveCategorie(null);
        } catch (Exception e) {
            return new ResponseEntity<Bag>(bag, null);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    
    

}
