package com.bookapicrud.model.DAO;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

    
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"user_name","email"}))
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_id_generator",allocationSize=10, initialValue = 11)
    @Column(name="user_id", updatable = false, nullable = false)
    private long id;

    @Column(name = "user_name", nullable = false)
    @NotNull(message = "Every user must have one user name")
    @NotBlank(message = "User name must not be blank") 
    private String username;

    @Column(name = "email", nullable = false)
    @NotNull(message = "Every user must have one email")
    @NotBlank(message = "User email must not be blank") 
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull(message = "Every user must have one password")
    @NotBlank(message = "User password must not be blank") 
    private String password;

    @Column(name = "activated", nullable = false)
    @NotNull(message = "Every user must have one state")
    private Boolean activated;

    @Column(name = "join_date", nullable = false)
    @NotNull(message = "Every user must have one join timestamp")
    private Timestamp joinDate;



    public User() {
    }

    public User(long id, String username, String email, String password, Boolean activated, Timestamp joinDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.activated = activated;
        this.joinDate = joinDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }
   
}


