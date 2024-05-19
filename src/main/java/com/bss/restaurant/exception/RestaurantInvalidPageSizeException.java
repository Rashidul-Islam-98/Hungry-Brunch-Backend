package com.bss.restaurant.exception;

import lombok.Getter;

@Getter
public class RestaurantInvalidPageSizeException extends RuntimeException {
    public RestaurantInvalidPageSizeException(String message) {
        super(message);
    }
}
