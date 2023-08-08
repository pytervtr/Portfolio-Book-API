package com.bookapicrud.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookapicrud.model.DAO.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token,String>{

    @Query(value = "SELECT * FROM tokens NATURAL JOIN users WHERE users.email=?1", nativeQuery = true)
    List<Token> retrieveAllActiveUserTokens(String author);

    @Query(value = "SELECT * FROM tokens WHERE expiry_date<?1", nativeQuery = true)
    List<Token> retrieveExpiredTokens(Date currenDate);
}
