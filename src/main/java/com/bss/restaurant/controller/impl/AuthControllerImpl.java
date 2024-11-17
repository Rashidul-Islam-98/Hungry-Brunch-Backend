package com.bss.restaurant.controller.impl;

import com.bss.restaurant.controller.AuthController;
import com.bss.restaurant.dto.request.RefreshTokenRequest;
import com.bss.restaurant.dto.request.RegisterRequest;
import com.bss.restaurant.dto.request.LoginRequest;
import com.bss.restaurant.dto.response.LoginResponse;
import com.bss.restaurant.dto.response.RefreshTokenResponse;
import com.bss.restaurant.dto.response.RestaurantBaseResponse;
import com.bss.restaurant.service.AuthService;
import com.bss.restaurant.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<RestaurantBaseResponse> register(RegisterRequest request) {
        authService.saveUser(request);
        var message = RestaurantBaseResponse.builder().message("Registration successful.").build();
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest request) {
        return ResponseEntity.ok(refreshTokenService.generateAccessToken(request));
    }
}
