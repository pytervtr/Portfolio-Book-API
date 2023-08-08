package com.bookapicrud.service;
import java.util.List;

import com.bookapicrud.model.DTO.LoginCredentials;
import com.bookapicrud.model.DTO.UserDTO;

public interface AuthenticationServiceInterface {

    UserDTO saveUser(UserDTO user);

    void activateAccount(String activateToken);

    List<String> loginUser(LoginCredentials userCredentials);

    String refreshUserToken( String refreshToken);

    void logoutUser(String refreshToken);
    
    void closeUserSessions(String refreshToken);

    void removeAllExpiredTokens();
    
    void requestNewPassword(LoginCredentials recoverCredentials);
        
    void changeUserPassword(LoginCredentials recoverCredentials, String recoverToken);
}
