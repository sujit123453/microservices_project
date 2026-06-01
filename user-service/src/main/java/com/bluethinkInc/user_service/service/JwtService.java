package com.bluethinkInc.user_service.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateAccessToken(String name, String role);

    String generateRefreshToken(String name);

    String extractUsername(String token);

    String extractTokenType(String token);

    boolean validateToken(String token, UserDetails userDetails);

    String extractRole(String token);
}
