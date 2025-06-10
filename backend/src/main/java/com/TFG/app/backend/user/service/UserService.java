package com.TFG.app.backend.user.service;

import com.TFG.app.backend.user.dto.SignUpRequest;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(SignUpRequest signUpRequest) {
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use"); // Lanza una excepción personalizada para el frontend
        }
        User newUser = new User();
        newUser.setName(signUpRequest.getFirstName());
        newUser.setSurname(signUpRequest.getLastName());
        newUser.setEmail(signUpRequest.getEmail());
         String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }
}
