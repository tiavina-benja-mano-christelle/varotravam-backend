package mg.company.varotravam.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.exceptions.NotAuthorizedException;
import mg.company.varotravam.models.Categorie;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.JWTtokens;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController extends MonController {

    @GetMapping("/pages")
    public ResponseEntity<Bag> getNbPage() {
        Bag bag = new Bag();
        try {
            bag.setData(new Categorie().getNbPage(null));
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    
    
    @GetMapping
    public ResponseEntity<Bag> getAll() {
        Bag bag = new Bag();
        try {
            bag.setData(new Categorie().getAll(null));
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
            bag.setData(new Categorie().getAll(null, start));
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Bag> add(@RequestBody Categorie transmission, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            transmission.save(null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }

    
    @PutMapping
    public ResponseEntity<Bag> update(@RequestBody Categorie transmission, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            transmission.update(null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }

    
    @DeleteMapping
    public ResponseEntity<Bag> delete(@RequestBody Categorie transmission, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            transmission.delete(transmission.getId(), null);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }

    
    
    

}
