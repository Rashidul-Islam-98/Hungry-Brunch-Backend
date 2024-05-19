package com.bss.restaurant.service;

import com.bss.restaurant.dto.request.RegisterRequest;
import com.bss.restaurant.dto.request.LoginRequest;
import com.bss.restaurant.dto.response.LoginResponse;

import java.util.UUID;

public interface AuthService {
    void saveUser(RegisterRequest authRegisterRequest);
    LoginResponse login(LoginRequest authRequest);
}
