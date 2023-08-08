package com.bookapicrud.security.Tokens;

import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class RecoverToken extends Token{
    
    private final static String RECOVER_TOKEN_SECRET = "secretRecoverTokenKey";

    private final static Long RECOVER_TOKEN_VALIDITY_SECONDS = 3600L;//1 h

    public RecoverToken(){}

    public String createRecoverToken(String email){
        return Jwts.builder()
        .setSubject(email)
        .setExpiration(new Date(System.currentTimeMillis() + RECOVER_TOKEN_VALIDITY_SECONDS * 1_000))
        .addClaims(new HashMap<String,Object>(){{ put("tokenType", "Recover");}})
        .signWith(SignatureAlgorithm.HS256, RECOVER_TOKEN_SECRET)
        .compact();
    }
    
    public Object extractClaim (String token, String name, Object object){
        return super.extractClaim(token, RECOVER_TOKEN_SECRET, name, object);
    }

    public Boolean isTokenExpired(String token){
        return super.isTokenExpired(token, RECOVER_TOKEN_SECRET);
    }

    protected Boolean hasClaim(String token, String claimName, Object obj){
        return super.hasClaim(token, RECOVER_TOKEN_SECRET, claimName, obj);
    }

    public Boolean isTokenValid(String token){
        return super.isTokenValid(token, RECOVER_TOKEN_SECRET, "Recover");
    }
}
