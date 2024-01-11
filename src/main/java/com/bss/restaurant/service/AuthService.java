package com.bss.restaurant.service;

import com.bss.restaurant.dto.request.AuthRegisterRequest;
import com.bss.restaurant.dto.request.AuthRequest;
import com.bss.restaurant.dto.response.AuthResponse;

import java.util.UUID;

public interface AuthService {
    void saveAdmin(AuthRegisterRequest authRegisterRequest);
    AuthResponse login(AuthRequest authRequest);

    void createUsernameAndPassword(UUID userId, AuthRequest request);
}
