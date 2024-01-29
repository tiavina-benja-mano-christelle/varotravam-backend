package mg.company.varotravam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.exceptions.NotAuthorizedException;
import mg.company.varotravam.models.Modele;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.JWTtokens;

@RestController
@RequestMapping("/api/v1/modeles")
@CrossOrigin
public class ModelesController extends MonController {
     
    @GetMapping("/pages")
    public ResponseEntity<Bag> getNbPage() {
        Bag bag = new Bag();
        try {
            bag.setData(new Modele().getNbPage(null));
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    
    @GetMapping("/{start}")
    public ResponseEntity<Bag> getAll(@PathVariable int start) {
        Bag bag = new Bag();
        try {
            bag.setData(new Modele().getAll(null, start));
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }


    /**
     * Récupère tous les modeles de véhicules
     * @return
     */
    @GetMapping
    public ResponseEntity<Bag> getAll() {
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
    public ResponseEntity<Bag> get(HttpServletRequest request, @PathVariable int marqueId) {
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
    public ResponseEntity<Bag> add(@RequestBody Modele modele, HttpServletRequest request) {
        try {
            JWTtokens.checkWithRole(request, "admin");
            modele.save(null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }

    /**
     * Met à jour un modele de véhicule
     * @param modeles
     * @return
     */
    @PutMapping
    public ResponseEntity<Bag> update(@RequestBody Modele modele, HttpServletRequest request) {
        try {
            JWTtokens.checkWithRole(request, "admin");
            modele.update(null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }

    /**
     * Met à jour un modele de véhicule
     * @param modeles
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Bag> delete(@RequestBody Modele modele, HttpServletRequest request) {
        try {
            JWTtokens.checkWithRole(request, "admin");
            modele.delete(modele.getId(), null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }
    
}
