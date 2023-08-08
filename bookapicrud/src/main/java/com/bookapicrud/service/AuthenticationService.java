package com.bookapicrud.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookapicrud.exception.types.APIRequestParameterException;
import com.bookapicrud.exception.types.APIResourceException;
import com.bookapicrud.model.DAO.Token;
import com.bookapicrud.model.DAO.User;
import com.bookapicrud.model.DTO.LoginCredentials;
import com.bookapicrud.model.DTO.UserDTO;
import com.bookapicrud.model.Mapper.UserMapper;
import com.bookapicrud.repository.TokenRepository;
import com.bookapicrud.repository.UserRepository;
import com.bookapicrud.security.Tokens.ActivateToken;
import com.bookapicrud.security.Tokens.BearerToken;
import com.bookapicrud.security.Tokens.RecoverToken;
import com.bookapicrud.security.Tokens.RefreshToken;

import io.jsonwebtoken.Claims;
import jakarta.mail.internet.MimeMessage;

import jakarta.validation.Validator;

@Service
public class AuthenticationService implements AuthenticationServiceInterface {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator authValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public UserDTO saveUser(UserDTO user) throws APIRequestParameterException, APIResourceException{//impl ok

        if(!authValidator.validate(user).isEmpty()){throw new APIRequestParameterException();}   
        if(isUserAlreadySave(user.getEmail(), user.getUsername())){ throw new APIResourceException("User parameters not available");}            

        Long time = new Date().getTime();

        User useDao = UserMapper.userDtoToDao(user);
        useDao.setPassword(passwordEncoder.encode(useDao.getPassword()));
        useDao.setActivated(false);
        useDao.setJoinDate(new Timestamp(time-(time%1000)));


        if(!authValidator.validate(useDao).isEmpty()){throw new APIRequestParameterException();}                    

        userRepository.save(useDao);

        String activationToken = new ActivateToken().createActivateToken(useDao.getEmail());

        Token activationTokenDao = new Token();

        activationTokenDao.setId(activationToken);
        activationTokenDao.setUser(useDao);
        activationTokenDao.setEexpirationDate(new java.sql.Date(((Date) new ActivateToken().extractClaim(activationToken, Claims.EXPIRATION, new Date())).getTime()));

        if(!authValidator.validate(activationToken).isEmpty()){throw new APIRequestParameterException();}

        tokenRepository.save(activationTokenDao);

        String[] emailData = {"Activate account", 
                            "Simple Book API te da la bienvenida. Por favor confirma que esta es tu dirección de email para poder comenzar a utilizar nuestros servios:", 
                            "/api/auth/activateAccount?token="+activationToken, 
                            "ACTIVATE ACCOUNT"};//[] String subject, String text, String url, String url tag]


        sendUserEmail(user, emailData);
        
        return user;
    }

    @Override
    public void activateAccount(String activateToken) throws APIRequestParameterException, APIResourceException{//impl ok

        if(!new ActivateToken().isTokenValid(activateToken) || tokenRepository.findById(activateToken).isEmpty()) {
                throw new APIRequestParameterException();
        }

        Optional<User> userDao = userRepository.retrieveUserByEmail(new ActivateToken().extractClaim(activateToken, Claims.SUBJECT, new String()));

        if(userDao.isEmpty()){throw new APIResourceException("User doesnt exist");}
        Long time = new Date().getTime();
        userDao.get().setJoinDate(new Timestamp(time-(time%1000)));
        userDao.get().setActivated(true);

        if(!authValidator.validate(userDao).isEmpty()){throw new APIRequestParameterException();}

        userRepository.save(userDao.get());

        tokenRepository.deleteById(activateToken);;
    }


    @Override
    public List<String> loginUser(LoginCredentials userCredentials) throws APIRequestParameterException, APIResourceException{//impl ok

        if(!authValidator.validate(userCredentials).isEmpty()){throw new APIRequestParameterException();}

        Optional<User> userDao = userRepository.retrieveUserByEmail(userCredentials.getEmail());
        if(userDao.isEmpty()){throw new APIResourceException("User doesnt exist");}
        else if(!userDao.get().getActivated()){throw new APIResourceException("User account is not activate");}


        SecurityContextHolder.clearContext();


        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userCredentials.getEmail(),
                userCredentials.getPassword(),
                Collections.emptyList())
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        

        String bearerToken = new BearerToken().createBearerToken(userCredentials.getEmail());
        String refreshToken = new RefreshToken().createRefreshToken(userCredentials.getEmail());

        if(bearerToken == "" || bearerToken == null || refreshToken == "" || refreshToken == null){throw new APIResourceException("Error while logging user");}

        Token tokenDao = new Token();
        tokenDao.setId(refreshToken);
        tokenDao.setUser(userDao.get());
        tokenDao.setEexpirationDate(new java.sql.Date(((Date) new RefreshToken().extractClaim(refreshToken, Claims.EXPIRATION, new Date())).getTime()));

        if(!authValidator.validate(tokenDao).isEmpty()){throw new APIRequestParameterException();}

        tokenRepository.save(tokenDao);

        return new ArrayList<String>(){{add(bearerToken); add(refreshToken);}};

    }

    
    @Override
    public void logoutUser(String refreshToken){//impl ok

        if(!new RefreshToken().isTokenValid(refreshToken) || tokenRepository.findById(refreshToken).isEmpty()){throw new APIRequestParameterException();}

        removeToken(refreshToken);

    }

    @Override
    public String refreshUserToken(String refreshToken){//impl ok

        if(!new RefreshToken().isTokenValid(refreshToken) || tokenRepository.findById(refreshToken).isEmpty()) {
                throw new APIRequestParameterException();
            }
            
        String newBearerToken = new BearerToken().createBearerToken((String) new RefreshToken().extractClaim(refreshToken, Claims.SUBJECT, new String()));

        return newBearerToken;

    }

    @Override
    public void requestNewPassword(LoginCredentials recoverCredentials){ //impl ok
        if(!authValidator.validate(recoverCredentials).isEmpty()){throw new APIRequestParameterException();}
        if(!isUserAlreadySave(recoverCredentials.getEmail(), null)){throw new APIResourceException("User requested doesnt exist");}

        User userDao = userRepository.retrieveUserByEmail(recoverCredentials.getEmail()).get();

        String recoverToken = new RecoverToken().createRecoverToken(recoverCredentials.getEmail());

        Token recoverTokenDao = new Token();

        recoverTokenDao.setId(recoverToken);
        recoverTokenDao.setUser(userDao);
        recoverTokenDao.setEexpirationDate(new java.sql.Date(((Date) new RecoverToken().extractClaim(recoverToken, Claims.EXPIRATION, new Date())).getTime()));



        if(!authValidator.validate(recoverTokenDao).isEmpty()){throw new APIRequestParameterException();}

 
        tokenRepository.save(recoverTokenDao);

        String[] emailData = {"Recover account", 
                            "Parece que no recuerdas tu contrase&ntilde;a, porfavor accede al siguente enlace para restablecerla:", 
                            "/api/auth/recoverPassword?token="+recoverToken, 
                            "RECOVER ACCOUNT"};//[] String subject, String text, String url, String url tag]
        
        

        
        sendUserEmail(UserMapper.userDaoToDto(userDao), emailData);
        
    }   

    @Override
    public void changeUserPassword(LoginCredentials recoverCredentials, String recoverToken){//impl ok
        if(!authValidator.validate(recoverCredentials).isEmpty()){throw new APIRequestParameterException();}

        if(!new RecoverToken().isTokenValid(recoverToken) || tokenRepository.findById(recoverToken).isEmpty()) {
                throw new APIRequestParameterException();
        }
        String mail = (String) (new RecoverToken().extractClaim(recoverToken, Claims.SUBJECT, new String()));
        if(!isUserAlreadySave( mail, null)){throw new APIResourceException("User requested doesnt exist");}
        if(recoverCredentials.getPassword() == "" || recoverCredentials.getPassword() == null){throw new APIRequestParameterException();}
        User userDao = userRepository.retrieveUserByEmail(mail).get();

        userDao.setPassword(passwordEncoder.encode(recoverCredentials.getPassword()));

        userRepository.save(userDao);
        tokenRepository.deleteById(recoverToken);
        
    }

    @Override
    public void closeUserSessions(String refreshToken){

        if(!new RefreshToken().isTokenValid(refreshToken) || tokenRepository.findById(refreshToken).isEmpty()){throw new APIRequestParameterException();}

        User userTokensDao = tokenRepository.findById(refreshToken).get().getUser();

        List<Token> userActiveTokens = tokenRepository.retrieveAllActiveUserTokens(userTokensDao.getEmail());

        if(userActiveTokens.isEmpty()){return;}

        for(Token currentToken : userActiveTokens){
            removeToken(currentToken.getId());
        }

    }



    @Override
    public void removeAllExpiredTokens(){//comprobar
        Calendar calendar = Calendar.getInstance();

        List<Token> expiredTokenList = tokenRepository.retrieveExpiredTokens(new java.sql.Date(calendar.getTime().getTime()));

        for(Token expiredToken : expiredTokenList){
            removeToken(expiredToken.getId());
        }
    }

    private Boolean isUserAlreadySave(String email, String userName){
        Boolean result = false;

        result = result || userRepository.retrieveUserByEmail(email).isPresent();
        result = result || userRepository.retrieveUserByUserName(userName).isPresent();

        return result;
    }

    private void removeToken(String token){
        Optional<Token> currentToken = tokenRepository.findById(token);
        if(currentToken.isEmpty()){return;}

        tokenRepository.deleteById(token);
    }

    public void sendUserEmail(UserDTO user, String[] emailData){//[] String subject, String text, String url, String url tag]
        if(!authValidator.validate(user).isEmpty()){throw new APIRequestParameterException();}

        String fromAddress="developermailtesting15152@gmail.com";
        String toAddress = user.getEmail();
        String senderName =user.getUsername();
        String content="<table>"+
                            "<tbody>"+
                                "<tr>"+
                                    "<td>Hola "+senderName+".</td>"+
                                "</tr>"+
                                "<tr><td></td></tr>"+
                                "<tr>"+
                                    "<td>"+emailData[1]+"</td>"+
                                "</tr>"+
                                "<tr><td></td></tr>"+
                                "<tr>"+
                                    "<td><a href=http://localhost:8081/"+emailData[2]+">"+emailData[3]+"</a></td>"+
                                "</tr>"+
                            "</tbody>"+
                        "</table>";

        MimeMessage message = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromAddress);
            helper.setTo(toAddress);
            helper.setSubject(emailData[0]);
            helper.setText(content, true);

            javaMailSender.send(message);
        }catch(Exception e){
            throw new APIResourceException("Auth email couldnt be sent");
        }

    }

}
