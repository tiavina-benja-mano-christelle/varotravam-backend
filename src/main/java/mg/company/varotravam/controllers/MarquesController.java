package mg.company.varotravam.controllers;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.models.Marque;
import mg.company.varotravam.utils.Bag;

@RestController
@RequestMapping("/api/v1/marques")
public class MarquesController extends MonController {
     
    /**
     * Récupère toutes les marques de véhicules
     * @return
     */
    @GetMapping
    public ResponseEntity<Bag> recupererTout(HttpServletRequest request) {
        try {
            bag.setData(new Marque().getAllMarque(null));
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Ajouter une nouvelle marque de véhicule
     * @param marque
     * @return
     */
    @PostMapping
    public ResponseEntity<Bag> ajouter(@RequestBody Marque marque, HttpServletRequest request) {
        try {
            marque.saveMarque(null);
        } catch (Exception e) {
            return new ResponseEntity<Bag>(bag, null);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    
}
