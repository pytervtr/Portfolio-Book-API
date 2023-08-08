package com.bookapicrud.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDTO {

    @NotNull(message = "Every user must have one user name")
    @NotBlank(message = "User name must not be blank") 
    @JsonProperty("username")
    private String username;

    @NotNull(message = "Every user must have one email")
    @NotBlank(message = "User email must not be blank") 
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Every user must have one password")
    @NotBlank(message = "User password must not be blank") 
    @JsonProperty("password")
    private String password;

    public UserDTO() {
    }

    public UserDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
    
}
