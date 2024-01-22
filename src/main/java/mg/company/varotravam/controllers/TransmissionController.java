package mg.company.varotravam.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.company.varotravam.models.Transmission;
import mg.company.varotravam.utils.Bag;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/boite-vitesses")
public class TransmissionController extends MonController {
    
    @GetMapping
    public ResponseEntity<Bag> recupererTout() {
        try {
            bag.setData(new Transmission().getAll(null));
        } catch (ClassNotFoundException | SQLException e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Bag> ajouter(@RequestBody Transmission boiteVitesse) {
        try {
            boiteVitesse.save(null);
        } catch (ClassNotFoundException | SQLException e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    
    

}
