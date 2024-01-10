package mg.company.varotravam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
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
    Claims recupererTokens(HttpServletRequest request) throws Exception {
        String autorisationHeaders = request.getHeader(HttpHeaders.AUTHORIZATION);
        Claims tokens = JWTtokens.checkBearer(autorisationHeaders);
        return tokens;
    }
}
