package com.bss.restaurant.service;

import com.bss.restaurant.dto.request.LoginRequest;
import com.bss.restaurant.dto.request.SetPasswordRequest;
import com.bss.restaurant.dto.response.UserResponse;
import com.bss.restaurant.entity.User;

import java.util.UUID;

public interface UserService {
    User getUserByUsername(String username);
    User getAuthenticatedUser();
    UserResponse getUser(UUID id);
    void createUsernameAndPassword(SetPasswordRequest request);
}
