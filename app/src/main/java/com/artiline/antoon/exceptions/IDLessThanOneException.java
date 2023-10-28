package com.artiline.antoon.exceptions;

public class IDLessThanOneException extends RuntimeException {

    public IDLessThanOneException(){

    }
    public IDLessThanOneException(Throwable cause){
        super(cause);
    }

    public IDLessThanOneException(String message){
        super(message);
    }

    public IDLessThanOneException(String message, Throwable cause){
        super(message, cause);
    }
}
