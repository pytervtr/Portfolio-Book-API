package com.bookapicrud.exception.types;

public class APIResourceException extends RuntimeException{
     
    public APIResourceException(String message){
        super(message);
    }

    public APIResourceException(String message, Throwable cause){
        super(message, cause);
    }
}
