package com.leocalheiros.paymentsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class BadCredentialsException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public BadCredentialsException(String ex){
        super(ex);
    }
}
