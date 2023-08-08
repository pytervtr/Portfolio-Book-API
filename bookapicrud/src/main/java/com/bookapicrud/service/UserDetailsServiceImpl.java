package com.bookapicrud.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookapicrud.model.DAO.User;
import com.bookapicrud.model.Security.UserDetailsImpl;
import com.bookapicrud.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Optional<User> userDao = userRepository.retrieveUserByEmail(email);
        if(userDao.isEmpty()){
            throw new UsernameNotFoundException("The user with the id" + email + " doesn't exist");
        }

        return new UserDetailsImpl(userDao.get());

    }
    
}
