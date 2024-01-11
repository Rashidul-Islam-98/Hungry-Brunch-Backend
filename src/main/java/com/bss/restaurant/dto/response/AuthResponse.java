package com.bss.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private UserResponse user;
    private String token;
}
