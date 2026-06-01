package com.bluethinkInc.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String access_token;
    private String refresh_token;
    private Long user_id;
    private String user_name;
    private String user_email;
    private String user_password;
    private String user_role;

    public LoginResponse(String accessToken, String refreshToken, Long id, String name, String email, String role) {
        this.access_token = accessToken;
        this.refresh_token = refreshToken;
        this.user_id = id;
        this.user_name = name;
        this.user_email = email;
        this.user_role = role;
    }
}