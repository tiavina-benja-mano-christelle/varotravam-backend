package mg.company.varotravam.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.exceptions.NotAuthorizedException;
import mg.company.varotravam.models.Marque;
import mg.company.varotravam.models.Modele;
import mg.company.varotravam.models.StatUtilisateur;
import mg.company.varotravam.models.Utilisateur;
import mg.company.varotravam.models.viewmodel.StatViewModel;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.JWTtokens;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/stats")
public class StatistiquesController {
    
    /**
     * Controller pour récupérer tous les stats
     * @param request
     * @return
     */
    @GetMapping
    public ResponseEntity<Bag> getAllStat(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(new StatViewModel()); //TODO: implement fonction
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Controller pour récupérer la commission
     * @param request
     * @return
     */
    @GetMapping("/commission")
    public ResponseEntity<Bag> getCommission(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(23.4); //TODO implement la récupération de la commission
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    
    /**
     * Controller pour récupérer le nombre d'annonce disponible
     * @param request
     * @return
     */
    @GetMapping("/nb-annonce-dispo")
    public ResponseEntity<Bag> getNbAnnonceDisponible(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(48); //TODO implement la récupération du nombre d'annonce disponible
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Controller pour récupérer le nombre total d'utilisateur inscrit
     * @param request
     * @return
     */
    @GetMapping("/nb-utilisateur-inscrit")
    public ResponseEntity<Bag> getNbUtilisateurInscrit(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(Utilisateur.getInscrit(null));
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Controller pour récupérer le graphe du nombre de nouvelle annonce par mois/ans
     * @param request
     * @return
     */
    @GetMapping("/graphe-nouvelle-annonce")
    public ResponseEntity<Bag> getGrapheNouvelleAnnonce(@RequestParam(defaultValue = "1", required = false) int annee, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(122); //TODO implement la récupération du nombre de nouvelle annonce par mois/ans
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Controller pour récupérer le graphe du nombre de nouvelle annonce par mois/ans
     * @param request
     * @return
     */
    @GetMapping("/graphe-annonce-vendue")
    public ResponseEntity<Bag> getGrapheAnnonceVendue(@RequestParam(defaultValue = "1", required = false) int annee, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(122); //TODO implement la récupération du nombre de nouvelle annonce par mois/ans
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Controller pour récupérer le graphe des meilleurs modeles vendues
     * @param request
     * @return
     */
    @GetMapping("/meilleur-modele-vendue")
    public ResponseEntity<Bag> getMeilleurModeleVendue(@RequestParam(defaultValue = "1", required = false) int annee, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(122); //TODO implement la récupération du graphe des meilleurs modèles vendues
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Controller pour récupérer le graphe des meilleurs marques vendues
     * @param request
     * @return
     */
    @GetMapping("/meilleur-marque-vendue")
    public ResponseEntity<Bag> getMeilleurMarqueVendue(@RequestParam(defaultValue = "1", required = false) int annee, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(122); //TODO implement la récupération du graphe des meilleurs marques vendues
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    
    /**
     * Controller pour récupérer le graphe du chiffres d'affaire par mois/ans
     * @param request
     * @return
     */
    @GetMapping("/graphe-chiffre-affaire")
    public ResponseEntity<Bag> getGrapheChiffreAffaire(@RequestParam(defaultValue = "1", required = false) int annee, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(122); //TODO implement la récupération du graphe des meilleurs marques vendues
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Controller pour récupérer le graphe des meilleurs marques vendues
     * @param request
     * @return
     */
    @GetMapping("/chiffre-affaire")
    public ResponseEntity<Bag> getChiffreAffaire(@RequestParam(defaultValue = "1", required = false) int annee, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(122); //TODO implement la récupération du graphe des meilleurs marques vendues
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Récupère un graphe du nombre d'utilisateur
     * @param request
     * @return
     */
    @GetMapping("/utilisateurs")
    public ResponseEntity<Bag> getNombreUtilisateur(@RequestParam(defaultValue = "1", required = false) int annee, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(StatUtilisateur.findData(annee, null)); 
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Controller pour récupérer le graphe des meilleurs marques vendues
     * @param request
     * @return
     */
    @GetMapping("/meilleur-vendeur")
    public ResponseEntity<Bag> getMeilleurVendeur(@RequestParam(defaultValue = "1", required = false) int annee, HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(122); //TODO implement la récupération du graphe des meilleurs marques vendues
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Récupère la marque la plus vendue du site
     * @param request
     * @return
     */
    @GetMapping("/marque")
    public ResponseEntity<Bag> getMeilleurMarqueOne(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(new Marque().getMostSelledOne(null)); 
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Récupère la marque la plus vendue du site
     * @param request
     * @return
     */
    @GetMapping("/marques")
    public ResponseEntity<Bag> getMeilleurMarque(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(new Marque().getMostSelled(null));
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }
    /**
     * Récupère la marque la plus vendue du site
     * @param request
     * @return
     */

    @GetMapping("/modele")
    public ResponseEntity<Bag> getMeilleurModeleOne(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(new Modele().getMostSelledOne(null)); 
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }

    /**
     * Récupère la marque la plus vendue du site
     * @param request
     * @return
     */
    @GetMapping("/modeles")
    public ResponseEntity<Bag> getMeilleurModele(HttpServletRequest request) {
        Bag bag = new Bag();
        try {
            JWTtokens.checkWithRole(request, "admin");
            bag.setData(new Modele().getMostSelled(null));
        } catch (NotAuthorizedException ex) {
            return new ResponseEntity<Bag>(HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<Bag>(bag, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bag>(bag, HttpStatus.OK);
    }





    
}
