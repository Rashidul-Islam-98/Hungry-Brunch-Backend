package com.bss.restaurant.dto.request;

import lombok.Getter;

@Getter
public class SetPasswordRequest {
    private String password;
    private String confirmPassword;
}
