package com.bss.restaurant.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60L;

    private String secret = "yDdcFApzjC9+sezBGqCu8zQQXlOTgu6bgxsNB2w3SiXLnIBNjYYYWZbZ9344WWUcigeE/IxwdvZb4ta3Jbe01CUp9F24FRIIjwWcyAA2xfdwi3qo7aCpcjlJaR44lwhu5C3k/vfp2RLfaeLVdk4HtILo1GEMU6QF1V0wN5PWEEKu6mOBQfiUnsgzIJv9L/gZH4Lg3zMrzlTYXxzd07ZKyJS6LbVspYFqX3O9iw4Y0gGEAZ4XGuAtpaFmokIEs+nBaxdMStkONmqeaUG/mnWoFSLwwtnlj14B222+2s4aOll0DISik/OvL6uJa07BeS6P1/cFnyREKA6C+rnFDUzjXQWSTsyxTgIegpU59Kr6Ve4HXj/t1YODKOxnQxC4xf65llASLSDBI0c8LXMtRrk6MYXQ8DaEOUNipC0rIxtn4L17Be83ROS1NijVtRRkgds3izfRduckOUOjl6YSMcvCRE3MWleLRlmJkrdClthc+9geL7/YmSlf1r+xU5U71pC5khsPlq3omgNgxIM6h45RFr16hLmkVsbYoRpePuNpQH3sWdetmtWDxBohmW1MTfXbbNiW5VIYGmkji9t/mEjWS/GeQXOCmtM4cK/i/qcUAsSpO4yHgstUYHWhDBJt3Dy+ijWQnJpscwXmIkeGYARFZOpBpBgK84AANkl7ZwnuFlBkxdHGOBVjJczyVkVldw6Y";
    byte[] secretKeyBytes = secret.getBytes(StandardCharsets.UTF_8);

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKeyBytes)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
