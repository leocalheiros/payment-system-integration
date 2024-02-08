package com.leocalheiros.paymentsystem.exceptions;

public class PixQRCodeLocationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public PixQRCodeLocationException(String ex){
        super(ex);
    }
}

