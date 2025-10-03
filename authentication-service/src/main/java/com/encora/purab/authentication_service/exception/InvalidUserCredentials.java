package com.encora.purab.authentication_service.exception;

public class InvalidUserCredentials extends RuntimeException{
    public InvalidUserCredentials(String message){
        super(message);
    }
}
