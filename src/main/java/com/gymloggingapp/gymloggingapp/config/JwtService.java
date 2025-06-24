package com.gymloggingapp.gymloggingapp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "Qa6u6TXdxOQhH4389fLs4l6HRgO0vXJQi0AlV62EO1B5MjhiNJ+gRqotio/SJ+hFiyb8ZTkVyZOsjlV+cnNrBtj4DPjlf7HszczXOItZrM1Onb22OAI/p5z/3+ZMWoLtllJDru7a/aQiSMOqNpy00mTp9aAZ9FIKoDQ5tOcKFnd7b+qB5UYzE4XoU0BGzw3JRZwg0zacklajcKlMdzoB3v4qddDCJ5vHaIO8B0Caj8GO77jkCw726LvZQSwh9cEy30bFiSJw3VBKcQfPHll3rNGxKlf8rXZTLydH95xNIxCob/K1r6anQ8JyyYJFjWS2b0FWDYmpEd7GDwuskmVffb8ask7pcpsrxEkzvRo2NQM="
    
    private String extractUserEmail(String token){
    return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private boolean isTokenValid(String token, UserDetails userDetails){

        return (extractUserEmail(token).equals(userDetails.getUsername()))&&!isTokenExpired(token);
    }

    private Key getSignInKey() {
        byte[] keyDecode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyDecode);
    }

    private String generateToken(UserDetails userDetails){
        Map<String, Object> extraClaims = new HashMap<>();
        return generateToken(extraClaims, userDetails);
    }


}
