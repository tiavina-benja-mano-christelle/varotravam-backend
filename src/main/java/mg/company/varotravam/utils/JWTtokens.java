package mg.company.varotravam.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JWTtokens {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String create(String subject, long ttlMillis) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + ttlMillis);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String create(int subject, long ttlMillis) {
        return create(String.valueOf(subject), ttlMillis);
    }

    public static Claims check(String jwt) throws Exception {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt);
            Claims claims = claimsJws.getBody();
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issued At: " + claims.getIssuedAt());
            System.out.println("Expiration: " + claims.getExpiration());
            return claims;
        } catch (Exception e) {
            throw new Exception("NOT AUTHORIZED");
        }
    }

    public static Claims checkBearer(String jwt) throws Exception {
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

}
