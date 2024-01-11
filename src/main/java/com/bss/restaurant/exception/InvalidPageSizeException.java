package com.bss.restaurant.exception;

import lombok.Getter;

@Getter
public class InvalidPageSizeException extends RuntimeException {
    public InvalidPageSizeException(String message) {
        super(message);
    }
}
