package com.bluethinkInc.user_service.service;

import com.bluethinkInc.user_service.dto.LoginRequest;
import com.bluethinkInc.user_service.dto.LoginResponse;
import com.bluethinkInc.user_service.dto.customResponse.UserLoginResponseEntity;
import com.bluethinkInc.user_service.model.User;
import org.springframework.stereotype.Service;


public interface AuthService {
    UserLoginResponseEntity<User> registerService(User user);

    LoginResponse loginService(LoginRequest loginRequest);
}
