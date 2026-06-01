package com.bluethinkInc.user_service.service.impl;

import com.bluethinkInc.user_service.dto.LoginRequest;
import com.bluethinkInc.user_service.dto.LoginResponse;
import com.bluethinkInc.user_service.dto.customResponse.UserLoginResponseEntity;
import com.bluethinkInc.user_service.model.ActiveUser;
import com.bluethinkInc.user_service.model.User;
import com.bluethinkInc.user_service.repository.ActiveUserRepo;
import com.bluethinkInc.user_service.repository.UserRepo;
import com.bluethinkInc.user_service.service.AuthService;
import com.bluethinkInc.user_service.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final ActiveUserRepo activeUserRepo;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(UserRepo userRepo, JwtService jwtService,
                           AuthenticationManager authManager,
                           PasswordEncoder encoder,ActiveUserRepo activeUserRepo) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
        this.authManager = authManager;
        this.encoder = encoder;
        this.activeUserRepo = activeUserRepo;
    }

    @Override
    public UserLoginResponseEntity<User> registerService(User user) {
        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);
        logger.info("User saved Successfully in database.");
        return new UserLoginResponseEntity<>(
                "Registered Successfully",
                200,
                savedUser
        );
    }

    @Override
    public LoginResponse loginService(LoginRequest loginRequest) {
        try {
            Authentication authentication =
                    authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUser_email(),
                            loginRequest.getUser_password()));
            if (authentication.isAuthenticated()) {
                activeUserRepo.save(
                        new ActiveUser(
                                loginRequest.getUser_email(),
                                LocalDateTime.now(),
                                true
                        )
                );
                User dbUser = userRepo.findByEmail(loginRequest.getUser_email());
                String accessToken = jwtService.generateAccessToken( dbUser.getEmail(), dbUser.getRole());
                String refreshToken = jwtService.generateRefreshToken(dbUser.getEmail());
                logger.info("User logged in successfully");
                return new LoginResponse(
                        accessToken,
                        refreshToken,
                        dbUser.getId(),
                        dbUser.getName(),
                        dbUser.getEmail(),
                        dbUser.getRole()
                );
            }
            throw new RuntimeException("Authentication failed");
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
