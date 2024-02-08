package com.leocalheiros.paymentsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PixMaxEVPException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public PixMaxEVPException(String ex){
        super(ex);
    }
}
