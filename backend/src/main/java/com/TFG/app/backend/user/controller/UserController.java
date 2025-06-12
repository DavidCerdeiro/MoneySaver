package com.TFG.app.backend.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.dto.ForgotPasswordRequest;
import com.TFG.app.backend.user.dto.LogInRequest;
import com.TFG.app.backend.user.dto.SignUpRequest;
import com.TFG.app.backend.user.service.UserService;
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/login")
    public ResponseEntity<User> logInUser(@RequestBody LogInRequest logInRequest) {
        Optional<User> user = userService.authUser(logInRequest.getEmail(), logInRequest.getPassword());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);   
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<User> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        
        if (userService.forgotPassword(forgotPasswordRequest.getEmail(), forgotPasswordRequest.getLocale())) {
            return new ResponseEntity<>(HttpStatus.OK);   
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody SignUpRequest signUpRequest) {
        User newUser = userService.createUser(signUpRequest);
        if(newUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
