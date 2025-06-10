package com.TFG.app.backend.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.TFG.app.backend.user.entity.User;
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

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody SignUpRequest signUpRequest) {
        User newUser = userService.createUser(signUpRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
