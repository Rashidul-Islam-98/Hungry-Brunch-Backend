package com.bss.restaurant.exception;

public class NotFoundException extends NullPointerException {
    public NotFoundException(String message){
        super(message);
    }
}
