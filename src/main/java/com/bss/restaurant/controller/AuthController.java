package com.bss.restaurant.controller;

import com.bss.restaurant.dto.request.AuthRegisterRequest;
import com.bss.restaurant.dto.request.AuthRequest;
import com.bss.restaurant.dto.response.AuthResponse;
import com.bss.restaurant.dto.response.MessageResponse;
import com.bss.restaurant.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> registerAdmin(@RequestBody AuthRegisterRequest request) {
        authService.saveAdmin(request);
        var message = MessageResponse.builder().message("Admin Created Successfully.").build();
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> getLoggedIn(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateUsernameAndPassword(@PathVariable UUID id,@RequestBody AuthRequest request) {
        authService.createUsernameAndPassword(id, request);
        return ResponseEntity.ok(MessageResponse.builder().message("Username and Password Updated Successfully").build());
    }
}
