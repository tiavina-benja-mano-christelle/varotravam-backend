package mg.company.varotravam.controllers;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import mg.company.varotravam.models.Utilisateur;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.JWTtokens;

@RestController
public class MonController {
    Bag bag = new Bag();

    /**
     * Récupère le token venant de la requette 
     * @param request
     * @return
     * @throws Exception
     */
    int verifierTokens(HttpServletRequest request) throws Exception {
        String autorisationHeaders = request.getHeader(HttpHeaders.AUTHORIZATION);
        Claims tokens = JWTtokens.checkBearer(autorisationHeaders);
        int userID = Integer.parseInt(tokens.getSubject());
        return userID;
    }

    /**
     * Verifie si l'utilisateur connecté est administrateur ou non
     * @param utilisateurId
     * @param connection
     * @return
     */
    boolean verifierAdministrateur(int utilisateurId, Connection connection) {
        Utilisateur utilisateur = Utilisateur.chercherParId(utilisateurId, connection);
        if (utilisateur.isAdministrateur()) {
            return true;
        }
        return false;
    }

    /**
     * Verifie si l'utilisateur connecté est administrateur ou non
     * @param utilisateurId
     * @param connection
     * @return
     * @throws Exception
     */
    boolean verifierAdministrateur(HttpServletRequest request, Connection connection) throws Exception {
        int utilisateurId = verifierTokens(request);
        Utilisateur utilisateur = Utilisateur.chercherParId(utilisateurId, connection);
        if (utilisateur.isAdministrateur()) {
            return true;
        }
        return false;
    }
}
