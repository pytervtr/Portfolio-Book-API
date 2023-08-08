package com.bookapicrud.controller;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookapicrud.exception.types.APIRequestParameterException;
import com.bookapicrud.model.DTO.LoginCredentials;
import com.bookapicrud.model.DTO.UserDTO;
import com.bookapicrud.service.AuthenticationServiceInterface;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationServiceInterface authenticationService;


    @GetMapping("/test")
    public ResponseEntity<Map<String,String>> test(){
        return new ResponseEntity<Map<String,String>>
            (Map.of(
                "status","connection working", 
                "message", "hello world book"),
            HttpStatus.OK);
    }    

    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> registerUser(@RequestBody UserDTO newUser){//ok

        try{
            Base64.getDecoder().decode(newUser.getPassword());
        }catch(Exception e){
            throw new APIRequestParameterException();
        }

        String passwordDecoded = new String(Base64.getDecoder().decode(newUser.getPassword()));
        newUser.setPassword(passwordDecoded);
        
        authenticationService.saveUser(newUser);

        newUser.setPassword(null);
        
        return new ResponseEntity<Map<String,Object>>
            (Map.of(
                "status","request completed", 
                "message", newUser),
            HttpStatus.CREATED);
    }

    @PostMapping("/activateAccount")
    public ResponseEntity<Map<String,String>> activateAccount(@RequestParam(value = "token") String activateToken){//ok

        authenticationService.activateAccount(activateToken);
        return new ResponseEntity<Map<String,String>>
            (Map.of(
                "status","request completed", 
                "message", "User activated"),
            HttpStatus.OK);
    
    }

 
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(@RequestBody LoginCredentials loginCredentials){//ok

        try{
            Base64.getDecoder().decode(loginCredentials.getPassword());
        }catch(Exception e){
            throw new APIRequestParameterException();
        }

        String passwordDecoded = new String(Base64.getDecoder().decode(loginCredentials.getPassword()));
        loginCredentials.setPassword(passwordDecoded);

        List<String> result = authenticationService.loginUser(loginCredentials);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+result.get(0));
        headers.add("RefreshAuthorization", "Refresh "+result.get(1));

        return new ResponseEntity<Map<String,String>>(
            Map.of(
                "status","request completed",     
                "message", "user logged in correctly"),
            headers,
            HttpStatus.OK
        );
    }

    @PutMapping("/refreshToken")
    public ResponseEntity <Map<String,String>> refreshToken(@RequestHeader(value = "RefreshAuthorization") String refreshToken){//ok

        String newBearerToken = authenticationService.refreshUserToken(refreshToken.replace("Refresh ", ""));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+newBearerToken);

        return new ResponseEntity<Map<String,String>>(
            Map.of(
                "status","request completed", 
                "message", "jwt updated in correctly"),
            headers,
            HttpStatus.OK
        );    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String,String>> logoutUser(@RequestHeader(value = "RefreshAuthorization")  String refreshToken){//ok
        authenticationService.logoutUser(refreshToken);
        return new ResponseEntity<Map<String,String>>(
            Map.of(
                "status","request completed",                 
                "message", "user logged out correctly"),
            HttpStatus.GONE
        );
    }

    @PutMapping("/requestPassword")
    public ResponseEntity<Map<String,String>> changePassword(@RequestBody LoginCredentials recoverCredentials){//ok

        recoverCredentials.setPassword("-");
        authenticationService.requestNewPassword(recoverCredentials);
        return new ResponseEntity<Map<String,String>>(
            Map.of(
                "status","request completed",                 
                "message", "request accepted, please confirm your identity by entering the link sent to your email address"),
            HttpStatus.OK
        );
    }

    @PutMapping("/recoverPassword")
    public ResponseEntity<Map<String,String>> recoverPassword(@RequestParam(value = "token")  String recoverToken,@RequestBody LoginCredentials recoverCredentials){//ok
        
        try{            
            Base64.getDecoder().decode(recoverCredentials.getPassword());
        }catch(Exception e){
            throw new APIRequestParameterException();
        }

        String passwordDecoded = new String(Base64.getDecoder().decode(recoverCredentials.getPassword()));
        recoverCredentials.setPassword(passwordDecoded);
        recoverCredentials.setEmail("-");

        authenticationService.changeUserPassword(recoverCredentials, recoverToken);
        return new ResponseEntity<Map<String,String>>(
            Map.of(
                "status","request completed",                 
                "message", "request accepted, password updated"),
            HttpStatus.OK
        );
    }



}
