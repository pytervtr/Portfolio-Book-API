package com.bookapicrud.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookapicrud.model.DTO.UserDTO;
import com.bookapicrud.security.Tokens.BearerToken;
import com.bookapicrud.service.AuthenticationServiceInterface;
import com.bookapicrud.service.UserServiceInterface;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/users")
public class UserController {



    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private AuthenticationServiceInterface authService;

    
    @GetMapping("/test")
    public ResponseEntity<Map<String,String>> test(){
        return new ResponseEntity<Map<String,String>>
            (Map.of(
                "status","connection working", 
                "message", "hello world user"),
            HttpStatus.OK);
    }
    
    @GetMapping("")
    public ResponseEntity<Map<String,Object>> retrieveUserData(@RequestHeader(value = "Authorization") String authToken){

        UserDTO userInfoDTO = userService.retrieveUserByEmail((String) new BearerToken().extractClaim(authToken.replace("Bearer ", ""), Claims.SUBJECT, new String()));


        return new ResponseEntity<Map<String,Object>>
            (Map.of(
                "status","request completed", 
                "message", userInfoDTO),
            HttpStatus.OK); 

    }
    


    @DeleteMapping("/remove")
    public ResponseEntity<Map<String,Object>> removeUser(@RequestHeader(value = "Authorization") String token, 
                                                        @RequestHeader(value = "RefreshAuthorization") String refreshToken){

        String tokenFiltered = token.replace("Bearer ", "");
        String refreshTokenFiltered = refreshToken.replace("Refresh ", "");
        

        authService.closeUserSessions(refreshTokenFiltered);

        userService.removeUser(tokenFiltered);
        
        return new ResponseEntity<Map<String,Object>>
            (Map.of(
                "status","request completed", 
                "mesage", "user deleted"),
            HttpStatus.GONE);    
        }    
}
