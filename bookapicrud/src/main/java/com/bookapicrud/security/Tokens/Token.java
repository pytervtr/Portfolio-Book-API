package com.bookapicrud.security.Tokens;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

public class Token {
    

    private Claims extractAllClaims (String token, String key){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
    
    protected Object extractClaim (String token, String key, String name, Object object){
        final Claims claims = extractAllClaims(token, key);
        return claims.get(name, object.getClass());
    }

    protected Boolean isTokenExpired(String token, String key){
        try{
            return ((Date)((Claims)Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody()).get(Claims.EXPIRATION, new Date().getClass())).before(new Date());
        }catch(ExpiredJwtException expJWT){
            return true;
        }
    }

    protected Boolean hasClaim(String token, String key,String claimName, Object obj){
        return extractClaim(token, key, claimName, obj) != null;
    }

    protected Boolean isTokenValid(String token, String key, String type){
        try{

            return (hasClaim(token, key, Claims.SUBJECT, new String()) && hasClaim(token, key, Claims.EXPIRATION, new Date()) &&
                        !isTokenExpired(token, key) &&
                        hasClaim(token, key, "tokenType", new String()) && extractClaim(token, key, "tokenType", new String()).equals(type));
                 
        }
        catch(ExpiredJwtException e ){
            return false;
        }
        catch(SignatureException e){
            return false;
        }
        catch(MalformedJwtException e){
            return false;
        }

    }
}

