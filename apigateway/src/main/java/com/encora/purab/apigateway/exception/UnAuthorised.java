package com.encora.purab.apigateway.exception;

public class UnAuthorised extends RuntimeException{
    public UnAuthorised(String message){
        super(message);
    }
}
