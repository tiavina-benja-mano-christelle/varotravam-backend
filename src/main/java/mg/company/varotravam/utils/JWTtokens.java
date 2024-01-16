package mg.company.varotravam.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JWTtokens {

    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    /**
     * Crée un nouveau token
     * @param subject
     * @return 
     */
    public static String create(Integer subject) {
        return create(String.valueOf(subject));
    }
    
    /**
     * Crée un nouveau token
     * @param subject
     * @return 
     */
    public static String create(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(secretKey)
                .compact();
    }

    /**
     * retourne le sujet si le token est valide
     * @param jwt
     * @return
     * @throws Exception 
     */
    public static int check(String jwt) throws Exception {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt);
            return extractSubject(jwt);
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Verify si le tokens est bearer et retourne le sujet si le token est valide
     * @param jwt
     * @return
     * @throws Exception 
     */
    public static int checkBearer(String jwt) throws Exception {
        try {
            if (jwt != null && jwt.startsWith("Bearer ")) {
                String token = jwt.substring(7); 
                return check(token);
            }
        } catch (Exception e) {
            throw e;
        }
        throw new Exception("NOT AUTHORIZED");
    }

    /**
     * Récupère le sujet dans le token
     * @param jwt
     * @return
     * @throws Exception 
     */
    private static int extractSubject(String jwt) throws Exception {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
            return Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            throw e;
        }
    }

}
