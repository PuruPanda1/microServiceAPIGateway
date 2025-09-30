package com.encora.purab.order_service.exception;

public class EmptyOrderException extends RuntimeException{
    public EmptyOrderException(String message){
        super(message);
    }
}
