package tp.eni_store.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tp.eni_store.bo.Person;
import tp.eni_store.dao.IDAOArticle;
import tp.eni_store.dao.IDAOPerson;

import java.security.Key;
import java.util.Date;

@Service
public class AuthService {

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    private final IDAOArticle daoArticle;
    private final IDAOPerson daoPerson;

    public AuthService(IDAOArticle daoArticle, IDAOPerson daoPerson) {
        this.daoArticle = daoArticle;
        this.daoPerson = daoPerson;
    }

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

    public ServiceResponse<String> createToken(String email, String password) {
        Person loggedPerson = daoPerson.selectPersonByLogin(email, password);

        if (loggedPerson == null){
            return ServiceHelper.buildResponse(716, null, "Couple email/mot de passe incorrect");
        }

        // Pour genere un token
// -- la date ou ca été crée (IssueAt)
// -- une date d'expiration (Expiration)
// -- une donnée subjectif (Subject)
// -- l'algo pour crypter (HS256)
// -- la clé secrête

// -- temps de vie du token
        Date tokenLifetime = new Date(System.currentTimeMillis() + (1000 * 60 * 60) * 1);

// Le code qui genere le token
        String token = Jwts.builder()
                .setSubject("quelquun@gmail.com")
                .issuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(tokenLifetime)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();

        return ServiceHelper.buildResponse(204, token, "Authentifié(e) avec succès");
    }

    public ServiceResponse<Boolean> checkToken(String token) {
// Error: 1 - Si Empty
        if (token.isEmpty()){
            return ServiceHelper.buildResponse(704, null, "Token vide");
        }

        // token trop court, pas possible de substring 7 sinon crash
        if (token.length() < 7){
            return ServiceHelper.buildResponse(704, null, "Token trop court");
        }

        // Récupérer le token dans le header authorization
// On substring 7 caractères car le header contient "Bearer tontoken"
        token = token.substring(7);

        try {
            // Outil pour récupérer le token (déchiffrer)
            JwtParser jwtParser = Jwts.parser().setSigningKey(getSecretKey()).build();

            // -- récupérer les claims de mon token (claims => toutes les info)
            Claims claims = jwtParser.parseSignedClaims(token).getBody();

            // Récupérer la date
            // 1: Version abstraite
            // Function<Claims, Date> expirationFunction = Claims::getExpiration;
            // Date expirationDate = expirationFunction.apply(claims);
            // 2: Version explicite
            Date expirationDate = claims.getExpiration();

        } catch (Exception e) {
            // Si la date d'expiration est depassé alors
            // Si exception jwt de type expiration
            if (e instanceof ExpiredJwtException){
                return ServiceHelper.buildResponse(704, null, "Token expiré");
            }

            // Si token malformé
            if (e instanceof MalformedJwtException){
                return ServiceHelper.buildResponse(704, null, "Token malformé");
            }

            return ServiceHelper.buildResponse(704, null, "Erreur inconnu");

        }

        return ServiceHelper.buildResponse(202, null, "Token valide");

    }

}
