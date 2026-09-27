package com.MrSuraj.eco.ecommerce.Exception;

import org.springframework.http.HttpStatus;

public class UserException extends Exception {
    private final HttpStatus status;

    public UserException(String message){
        this(message, HttpStatus.BAD_REQUEST);
    }

    public UserException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
