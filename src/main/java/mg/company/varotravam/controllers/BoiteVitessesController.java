package mg.company.varotravam.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.company.varotravam.models.BoiteVitesse;
import mg.company.varotravam.utils.Bag;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/boite-vitesses")
public class BoiteVitessesController extends MonController {
    
    @GetMapping
    public ResponseEntity<Bag> recupererTout() {
        try {
            bag.setData(new BoiteVitesse().getAllBoiteVitesse(null));
        } catch (ClassNotFoundException | SQLException e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Bag> ajouter(@RequestBody BoiteVitesse boiteVitesse) {
        try {
            boiteVitesse.saveBoiteVitesse(null);
        } catch (ClassNotFoundException | SQLException e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    
    

}
