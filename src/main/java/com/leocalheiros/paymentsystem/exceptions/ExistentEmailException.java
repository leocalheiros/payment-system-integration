package com.leocalheiros.paymentsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistentEmailException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ExistentEmailException(String ex){
        super(ex);
    }
}
