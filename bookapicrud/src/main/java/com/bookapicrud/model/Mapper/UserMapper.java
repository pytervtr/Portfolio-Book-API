package com.bookapicrud.model.Mapper;

import com.bookapicrud.model.DAO.User;
import com.bookapicrud.model.DTO.UserDTO;

public class UserMapper {
    
    public static UserDTO userDaoToDto(User userDao){
        if(userDao == null){return null;}
        
        UserDTO userDto = new UserDTO();

        userDto.setUsername(userDao.getUsername());
        userDto.setEmail(userDao.getEmail());
        userDto.setPassword(userDao.getPassword());

        return userDto;
    }

    public static User userDtoToDao(UserDTO userDto){
        if(userDto == null){return null;}
        
        User userDao = new User();

        userDao.setUsername(userDto.getUsername());
        userDao.setEmail(userDto.getEmail());
        userDao.setPassword(userDto.getPassword());


        return userDao;
    }
}
