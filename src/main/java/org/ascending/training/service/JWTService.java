package org.ascending.training.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.ascending.training.model.SystemUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class JWTService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    // put it into VM option and read it out
    private final String SECRET_KEY = System.getProperty("SECRET_KEY");
    private final String ISSUER = "com.ascending";
    private final long EXPIRATION_TIME = 86400 * 1000;

    public String generateToken(SystemUser systemUser) {
        // JWT signature algorithm
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // claims = payload
        Claims claims = Jwts.claims();
        claims.setId(String.valueOf(systemUser.getId()));
        claims.setSubject(systemUser.getName());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setIssuer(ISSUER);
        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));

        // set JWT claims
        JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, signingKey);

        // Builds the JWT and serializes it to a compact, URL-safe string, Generate token
        String generatedToken = builder.compact();
        return generatedToken;
    }

    public Claims decryptToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
        logger.info("Claims: " + claims.toString());
        return claims;
    }
}
