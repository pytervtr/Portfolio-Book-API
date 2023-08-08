package com.bookapicrud.security.Tokens;

import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ActivateToken extends Token{
    
    private final static String  ACTIVATE_TOKEN_SECRET = "secretActivateTokenKey";

    private final static Long ACTIVATE_TOKEN_VALIDITY_SECONDS = 3600L;//1 h

    public ActivateToken(){}

    public String createActivateToken(String email){
        return Jwts.builder()
        .setSubject(email)
        .setExpiration(new Date(System.currentTimeMillis() + ACTIVATE_TOKEN_VALIDITY_SECONDS * 1_000))
        .addClaims(new HashMap<String,Object>(){{ put("tokenType", "Activate");}})
        .signWith(SignatureAlgorithm.HS256, ACTIVATE_TOKEN_SECRET)
        .compact();
    }
    
    public Object extractClaim (String token, String name, Object object){
        return super.extractClaim(token, ACTIVATE_TOKEN_SECRET, name, object);
    }

    public Boolean isTokenExpired(String token){
        return super.isTokenExpired(token, ACTIVATE_TOKEN_SECRET);
    }

    protected Boolean hasClaim(String token, String claimName, Object obj){
        return super.hasClaim(token, ACTIVATE_TOKEN_SECRET, claimName, obj);
    }

    public Boolean isTokenValid(String token){
        return super.isTokenValid(token, ACTIVATE_TOKEN_SECRET, "Activate");
    }
}
