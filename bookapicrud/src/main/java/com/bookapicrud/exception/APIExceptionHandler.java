package com.bookapicrud.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bookapicrud.exception.types.APIRequestParameterException;
import com.bookapicrud.exception.types.APIResourceException;


@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value = {APIRequestParameterException.class})
    public ResponseEntity<Object> handleApiRequestException(APIRequestParameterException e){

        ApiException exception = new ApiException("error encountered", e.getMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {APIResourceException.class})
    public ResponseEntity<Object> handleApiRequestException(APIResourceException e){

        ApiException exception = new ApiException("error encountered", e.getMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
