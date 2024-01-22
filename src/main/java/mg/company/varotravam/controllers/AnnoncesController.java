package mg.company.varotravam.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.models.Annonce;
import mg.company.varotravam.models.Favori;
import mg.company.varotravam.models.Test;
import mg.company.varotravam.models.Utilisateur;
import mg.company.varotravam.services.FirebaseStorageService;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.JWTtokens;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

/* IMPORT */
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/* END */



@RequestMapping("/api/v1/annonces")
@RestController
public class AnnoncesController extends MonController {

    @Value("${firebase.storage.bucket}")
    private String bucketName;
    

    /**
     * APK TESTING MULTIPART-FILE
     */
    @PostMapping("/test")
    public ResponseEntity<Bag> test(Test test) {
        try {
            new FirebaseStorageService(null).envoyerImageVersFirebaseStorage(test.getFileValue(), "", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Crée une nouvelle annonce
     * @param annonce
     * @param request
     * @return
     */
    @PostMapping("/creation")
    public ResponseEntity<Bag> creerAnnonce(@RequestBody Annonce annonce, HttpServletRequest request) {
        //TODO: vérifier si le token est valide
        //TODO: implementer la création de l'annonce
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
    public ResponseEntity<Bag> annonceFavorites(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            String token = request.getHeader("authorization");
            int subject = JWTtokens.checkBearer(token);
            bag.setData(new Annonce().findFavori(subject, null));
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
        try {
            String token = request.getHeader("authorization");
            int subject = JWTtokens.checkBearer(token);
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(subject);
            Favori favori = new Favori(utilisateur.getId(), idAnnonce);
            favori.save(null);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }

    /**
     * Ajoute l'annonce en favori de l'utilisateur
     * @param annonce
     * @return
     */
    @DeleteMapping("/favorites")
    public ResponseEntity<Bag> enleverDesFavori(@RequestBody int idAnnonce, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            String token = request.getHeader("authorization");
            int subject = JWTtokens.checkBearer(token);
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(subject);
            Favori favori = new Favori(utilisateur.getId(), idAnnonce);
            favori.find(null);
            favori.delete(null);
        } catch (Exception e) {
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(HttpStatus.OK);
    }
}
