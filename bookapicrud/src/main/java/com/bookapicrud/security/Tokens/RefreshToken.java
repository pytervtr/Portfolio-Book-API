package com.bookapicrud.security.Tokens;

import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class RefreshToken extends Token{
    
    private final static String  REFRESH_TOKEN_SECRET = "secretRefreshTokenKey";

    private final static Long REFRESH_TOKEN_VALIDITY_SECONDS = 604800L;//1 week

    public RefreshToken(){}

    public String createRefreshToken(String email){
        return Jwts.builder()
        .setSubject(email)
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_SECONDS * 1_000))
        .addClaims(new HashMap<String,Object>(){{ put("tokenType", "Refresh");}})
        .signWith(SignatureAlgorithm.HS256, REFRESH_TOKEN_SECRET)
        .compact();
    }
    
    public Object extractClaim (String token, String name, Object object){
        return super.extractClaim(token, REFRESH_TOKEN_SECRET, name, object);
    }

    public Boolean isTokenExpired(String token){
        return super.isTokenExpired(token, REFRESH_TOKEN_SECRET);
    }

    protected Boolean hasClaim(String token, String claimName, Object obj){
        return super.hasClaim(token, REFRESH_TOKEN_SECRET, claimName, obj);
    }

    public Boolean isTokenValid(String token){
        return super.isTokenValid(token, REFRESH_TOKEN_SECRET, "Refresh");
    }
}
