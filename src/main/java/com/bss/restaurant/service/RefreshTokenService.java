package com.bss.restaurant.service;

import com.bss.restaurant.dto.request.RefreshTokenRequest;
import com.bss.restaurant.dto.response.RefreshTokenResponse;
import com.bss.restaurant.entity.RefreshToken;

public interface RefreshTokenService {
    String createRefreshToken(String userName);
    RefreshTokenResponse generateAccessToken(RefreshTokenRequest request);
    RefreshToken verifyRefreshToken(String token);
}
