package com.bluethinkInc.user_service.service;

import com.bluethinkInc.user_service.model.User;
import com.bluethinkInc.user_service.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }
    public void addUserService(User user) {
        userRepo.save(user);
    }

    public User getUserByIdService(Long id) {
        System.out.println("getUserByIdService method called with id: " + id);
        return userRepo.findById(id).orElse(null);
    }
}
