package com.bookapicrud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookapicrud.exception.types.APIRequestParameterException;
import com.bookapicrud.exception.types.APIResourceException;
import com.bookapicrud.model.DAO.User;
import com.bookapicrud.model.DTO.UserDTO;
import com.bookapicrud.model.Mapper.UserMapper;
import com.bookapicrud.repository.UserRepository;
import com.bookapicrud.security.Tokens.BearerToken;

import io.jsonwebtoken.Claims;
import jakarta.validation.Validator;

@Service
public class UserService implements UserServiceInterface {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Validator userValidator;




    @Override
    public UserDTO retrieveUserByEmail(String email) throws APIResourceException{

        if(!isUserAlreadySave(email, null)){throw new APIResourceException("User requested doesnt exist");}

        Optional<User> userDao = userRepository.retrieveUserByEmail(email);
        UserDTO userInfoDTO = UserMapper.userDaoToDto(userDao.get());
        if(!userValidator.validate(userInfoDTO).isEmpty()){throw new APIResourceException("User resouce doesnt exist");}

        userInfoDTO.setPassword(null);

        return userInfoDTO;
    }

    @Override
    public UserDTO retrieveUserByUserName(String userName) throws APIResourceException{

        if( !isUserAlreadySave(null, userName)){throw new APIResourceException("User requested doesnt exist");}

        Optional<User> userDao = userRepository.retrieveUserByUserName(userName);

        UserDTO userInfoDTO = UserMapper.userDaoToDto(userDao.get());
        if(!userValidator.validate(userInfoDTO).isEmpty()){throw new APIResourceException("User resouce doesnt exist");}

        return userInfoDTO;
    }

    @Override
    public UserDTO updateUserInfo(UserDTO user) throws APIRequestParameterException, APIResourceException{
        if(!userValidator.validate(user).isEmpty()){throw new APIRequestParameterException();}
        if( !isUserAlreadySave(user.getEmail(), user.getUsername())){throw new APIResourceException("User requested doesnt exist"); }

        User useDao = UserMapper.userDtoToDao(user);
        useDao.setPassword(passwordEncoder.encode(useDao.getPassword()));

        if(!userValidator.validate(useDao).isEmpty()){throw new APIRequestParameterException();}                    

        userRepository.save(useDao);
        return user;
        
    }

    @Override
    public void removeUser(String bearerToken) throws APIResourceException{

        if(!new BearerToken().isTokenValid(bearerToken)){throw new APIResourceException("Token error");}
        String tokenEmail = (String) new BearerToken().extractClaim(bearerToken, Claims.SUBJECT, new String());    

        if(!userRepository.areUserSessionsAlreadyClosed(tokenEmail)){throw new APIResourceException("There are already open session with this account");}

        if(!isUserAlreadySave(tokenEmail, "")){throw new APIResourceException("User requested doesnt exist");}

        User userDao = userRepository.retrieveUserByEmail(tokenEmail).get();
        userRepository.delete(userDao);
    }

    @Override
    public void removeNotActiveUsers(){
        List<User> notActiveList = userRepository.retrieveNotActiveUsers();

        for(User currentUser : notActiveList){
            userRepository.delete(currentUser);
        }
    }

    private Boolean isUserAlreadySave(String email, String userName){
        Boolean result = false;

        result = result || userRepository.retrieveUserByEmail(email).isPresent();
        result = result || userRepository.retrieveUserByUserName(userName).isPresent();

        return result;
    }



    
}
