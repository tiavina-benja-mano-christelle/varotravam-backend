package mg.company.varotravam.controllers;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.models.Modele;
import mg.company.varotravam.utils.Bag;

@RestController
@RequestMapping("/api/v1/modeles")
public class ModelesController extends MonController {
     
    /**
     * Récupère tous les modeles de véhicules
     * @return
     */
    @GetMapping
    public ResponseEntity<Bag> recupererTout(HttpServletRequest request) {
        try {
            bag.setData(new Modele().getAll(null));
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Récupère tous les modeles par rapport à une marque
     * @return
     */
    @GetMapping("/marque/{marqueId}")
    public ResponseEntity<Bag> recupererTout(HttpServletRequest request, @PathVariable int marqueId) {
        // try {
        //     bag.setData(new Categorie().getAllCategorie(null));
        //     if (1==1) throw new SQLException();
        //     throw new ClassNotFoundException();
        // } catch (ClassNotFoundException | SQLException e) {
        //     bag.setError(e.getMessage());
        //     return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        // }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Ajouter un nouveau modele de véhicule
     * @param modeles
     * @return
     */
    @PostMapping
    public ResponseEntity<Bag> ajouter(@RequestBody Modele modele, HttpServletRequest request) {
        try {
            modele.save(null);
        } catch (Exception e) {
            return new ResponseEntity<Bag>(bag, null);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    
}
