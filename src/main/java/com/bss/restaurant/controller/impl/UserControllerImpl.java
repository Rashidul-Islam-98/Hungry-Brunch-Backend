package com.bss.restaurant.controller.impl;

import com.bss.restaurant.controller.UserController;
import com.bss.restaurant.dto.request.SetPasswordRequest;
import com.bss.restaurant.dto.response.RestaurantBaseResponse;
import com.bss.restaurant.dto.response.UserResponse;
import com.bss.restaurant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/")
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> getUser(UUID id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Override
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<RestaurantBaseResponse> updateUsernameAndPassword(SetPasswordRequest request) {
        userService.createUsernameAndPassword(request);
        return ResponseEntity.ok(RestaurantBaseResponse.builder().message("Username and Password Updated Successfully").build());
    }
}
