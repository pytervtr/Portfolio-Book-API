package com.bookapicrud.model.DAO;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tokens")
public class Token {
    
    @Id
    @Column(name="token_id", updatable = false, nullable = false)
    @NotBlank(message = "A token cannot be empty")
    @NotNull(message = "A token cannot be null")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Every token must have one user related")
    private User user;

    @Column(name = "expiration_date", nullable = false)
    @NotNull(message = "Every token must have one expiration date")
    private Date expirationDate;

    public Token() {
    }

    public Token(String id, @NotNull(message = "Every token must have one user related") User user,
            @NotNull(message = "Every token must have one expiration date") Date expirationDate) {
        this.id = id;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setEexpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    

}
