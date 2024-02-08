package com.leocalheiros.paymentsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PixGenericException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public PixGenericException(String ex){
        super(ex);
    }
}

