package com.events.eventsbooking.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secretKey}")
    private String secretKey;
    @Value("${security.jwt.expiration}")
    private Long expiration;

    private String tokenGeneration(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {

        return buildToken(claims, userDetails, expiration);

    }

    private String buildToken(Map<String, Object> extraClaims,
                              UserDetails userDetails,
                              Long expiration) {
        String roles = userDetails.getAuthorities().toString();

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signSecretkey())
                .claim("roles", roles)
                .compact();
    }

    private Key signSecretkey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }


    public String extractUserEmail(String jwtToken) {
        return extractClaims(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaims(String jwtToken, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(jwtToken);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith((SecretKey) signSecretkey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        String username = extractUserEmail(jwtToken);
        return (username.equals(userDetails.getUsername()) && !tokenExpired(jwtToken));

    }

    private boolean tokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaims(jwtToken, Claims::getExpiration);
    }
}
