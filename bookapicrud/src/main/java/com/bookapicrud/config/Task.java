package com.bookapicrud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bookapicrud.service.AuthenticationService;
import com.bookapicrud.service.UserServiceInterface;

@Component
public class Task {
    
    @Autowired
    private AuthenticationService authService;

    @Autowired
    private UserServiceInterface userService;

    @Scheduled(cron = "0 0 0 * * ?")//second minute hour day month - //every day at 00:00:00
    public void removeExpiredTokens(){

        authService.removeAllExpiredTokens();
    }    

    
    @Scheduled(cron = "0 0/30 * * * *")//second minute hour day month - //every 30 mins
    public void removeNotActiveUsers(){

        userService.removeNotActiveUsers();

    }   
}
