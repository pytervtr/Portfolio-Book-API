package com.bookapicrud.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginCredentials {
    @NotNull(message = "Every user must have one email")
    @NotBlank(message = "User email must not be blank") 
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Every user must have one password")
    @NotBlank(message = "User password must not be blank") 
    @JsonProperty("password")
    private String password;

    public LoginCredentials() {
    }

    public LoginCredentials( String email, String password) {
        this.email = email;
        this.password = password;
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
