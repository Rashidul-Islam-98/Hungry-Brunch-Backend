package com.bss.restaurant.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;
    private boolean rememberMe;
}
