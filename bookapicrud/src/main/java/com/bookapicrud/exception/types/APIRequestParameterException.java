package com.bookapicrud.exception.types;

public class APIRequestParameterException extends RuntimeException{
     
    public APIRequestParameterException(){
        super("Requested parameters doesnt match validity rules");
    }

    public APIRequestParameterException( Throwable cause){
        super("Requested parameters doesnt match validity rules", cause);
    }
}
