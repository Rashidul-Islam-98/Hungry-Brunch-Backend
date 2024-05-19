package com.bss.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;
    private boolean forceChangePassword;
}
