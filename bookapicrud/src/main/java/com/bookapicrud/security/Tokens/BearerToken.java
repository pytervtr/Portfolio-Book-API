package com.bookapicrud.security.Tokens;

import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class BearerToken extends Token{
    
    private final static String  ACCESS_TOKEN_SECRET = "secretBearerTokenKey";

    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 300L;//5 min

    public BearerToken(){}

    public String createBearerToken(String email){
        return Jwts.builder()
        .setSubject(email)
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1_000))
        .addClaims(new HashMap<String,Object>(){{ put("tokenType", "Bearer");}})
        .signWith(SignatureAlgorithm.HS256, ACCESS_TOKEN_SECRET)
        .compact();
    }
    
    public Object extractClaim (String token, String name, Object object){
        return super.extractClaim(token, ACCESS_TOKEN_SECRET, name, object);
    }

    public Boolean isTokenExpired(String token){
        return super.isTokenExpired(token, ACCESS_TOKEN_SECRET);
    }

    protected Boolean hasClaim(String token, String claimName, Object obj){
        return super.hasClaim(token, ACCESS_TOKEN_SECRET, claimName, obj);
    }

    public Boolean isTokenValid(String token){
        return super.isTokenValid(token, ACCESS_TOKEN_SECRET, "Bearer");
    }
}
