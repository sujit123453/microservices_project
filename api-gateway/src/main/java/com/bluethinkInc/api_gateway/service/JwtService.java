package com.bluethinkInc.api_gateway.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);

    String extractTokenType(String token);

    boolean validateToken(String token);

    boolean validateToken(String token,UserDetails userDetails);

    String extractRole(String token);
}
