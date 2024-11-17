package com.bss.restaurant.service.Impl;

import com.bss.restaurant.dao.RefreshTokenRepository;
import com.bss.restaurant.dto.request.RefreshTokenRequest;
import com.bss.restaurant.dto.response.RefreshTokenResponse;
import com.bss.restaurant.entity.RefreshToken;
import com.bss.restaurant.exception.RestaurantBadRequestException;
import com.bss.restaurant.security.UserDetailsImpl;
import com.bss.restaurant.service.RefreshTokenService;
import com.bss.restaurant.service.UserService;
import com.bss.restaurant.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private long REFRESH_TOKEN_VALIDITY = 5*60*60*1000;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String createRefreshToken(String userName) {
        var user = userService.getUserByUsername(userName);
        var refreshToken = user.getRefreshToken();
        if (refreshToken == null) {
            refreshToken = RefreshToken.builder()
                    .value(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY))
                    .user(user)
                    .build();
        } else {
            refreshToken.setExpiry(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY));
        }

        var savedRefreshToken = refreshTokenRepository.save(refreshToken);
        user.setRefreshToken(savedRefreshToken);
        return savedRefreshToken.getValue();
    }

    @Override
    public RefreshTokenResponse generateAccessToken(RefreshTokenRequest request) {
        var refreshToken = verifyRefreshToken(request.getToken());
        refreshToken.setExpiry(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY));
        refreshTokenRepository.save(refreshToken);
        var accessToken = jwtUtil.generateToken(new UserDetailsImpl(refreshToken.getUser()), refreshToken.getUser().getId());
        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getValue())
                .build();
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {
        var refreshToken = getRefreshToken(token);
        if(refreshToken.getExpiry().compareTo((Instant.now()))<0){
            refreshTokenRepository.delete(refreshToken);
            throw new RestaurantBadRequestException("Refresh Token Expired.");
        }
        return refreshToken;
    }

    private RefreshToken getRefreshToken(String token){
        var refreshToken = refreshTokenRepository.findByValue(token).orElseThrow(()->
                new RestaurantBadRequestException("Invalid Refresh Token"));
        return refreshToken;
    }
}
