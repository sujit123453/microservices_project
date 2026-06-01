package com.bluethinkInc.user_service.controller;

import com.bluethinkInc.user_service.dto.LoginRequest;
import com.bluethinkInc.user_service.dto.LoginResponse;
import com.bluethinkInc.user_service.dto.customResponse.UserLoginResponseEntity;
import com.bluethinkInc.user_service.model.User;
import com.bluethinkInc.user_service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserLoginResponseEntity<User>> registerController(
            @RequestBody User user) {
        UserLoginResponseEntity<User> response = authService.registerService(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseEntity<LoginResponse>> loginController(
            @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authService.loginService(loginRequest);
            return ResponseEntity.ok(
                    new UserLoginResponseEntity<>(
                            "Logged in Successfully",
                            200,
                            loginResponse
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(
                    new UserLoginResponseEntity<>(
                            "Login Failed: " + e.getMessage(),
                            401,
                            null
                    )
            );
        }
    }
}
