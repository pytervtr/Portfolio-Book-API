package com.bookapicrud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookapicrud.model.DAO.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    
    @Query(value = "SELECT * FROM users WHERE users.email=?1", nativeQuery = true)
    Optional<User> retrieveUserByEmail(Object object);

    @Query(value = "SELECT * FROM users WHERE users.user_name=?1", nativeQuery = true)
    Optional<User> retrieveUserByUserName(String userName);

    @Query(value = "SELECT COUNT(*)=0 FROM users NATURAL JOIN tokens WHERE users.email=?1 AND users.user_id=tokens.user_id;", nativeQuery = true)
    Boolean areUserSessionsAlreadyClosed(String userName);

    @Query(value = "SELECT * FROM users WHERE users.activated=false", nativeQuery = true)
    List<User> retrieveNotActiveUsers();
}
