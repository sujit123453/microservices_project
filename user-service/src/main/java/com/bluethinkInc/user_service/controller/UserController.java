package com.bluethinkInc.user_service.controller;

import com.bluethinkInc.user_service.model.User;
import com.bluethinkInc.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try{
            userService.addUserService(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(user);
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User not added:"+ e.getMessage());
        }
    }
    @GetMapping("/internal/{id}")
    public ResponseEntity<?> getUserByIdController(@PathVariable Long id){
        try{
            System.out.println("getUserByIdController try block");
            User user = userService.getUserByIdService(id);
            if(user == null){
                return ResponseEntity.
                        status(HttpStatus.NOT_FOUND)
                        .body("User not found with this id");
            }
            return ResponseEntity.ok(user);
        }catch(Exception e){
            System.out.println("catch block of controller");
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Something wrong:"+ e.getMessage());
        }
    }
}
