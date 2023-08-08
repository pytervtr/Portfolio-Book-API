package com.bookapicrud.service;

import com.bookapicrud.model.DTO.UserDTO;

public interface UserServiceInterface {
 
    UserDTO retrieveUserByEmail(String email);

    UserDTO retrieveUserByUserName(String userName);

    UserDTO updateUserInfo(UserDTO user);

    void removeUser(String bearerToken);

    void removeNotActiveUsers();
}
