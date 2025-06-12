package com.TFG.app.backend.user.service;

import com.TFG.app.backend.user.dto.SignUpRequest;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.infraestructure.EmailService;
import com.TFG.app.backend.user.repository.UserRepository;
import com.TFG.app.backend.infraestructure.otp.service.One_Time_PasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Locale;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final One_Time_PasswordService oneTimePasswordService;
    private final EmailService emailService;

    @Autowired
    private MessageSource messageSource;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, One_Time_PasswordService oneTimePasswordService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.oneTimePasswordService = oneTimePasswordService;
        this.emailService = emailService;
        
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean forgotPassword(String email, Locale locale) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            String otp = oneTimePasswordService.generateOTP(email);
            String subject = messageSource.getMessage("forgot.password.subject", null, locale);
            String body = messageSource.getMessage("forgot.password.body", new Object[]{otp}, locale);
            emailService.sendEmail(email, subject, body);

            return true;

        }
        return false;
    }
    public Optional<User> authUser(String email, String password) {
        if(userRepository.findByEmail(email).isPresent()){
            Optional<User> user = userRepository.findByEmail(email);
            if(passwordEncoder.matches(password, user.get().getPassword())) {
                return user;
            }
        }
        return null;
        
    }

    public User createUser(SignUpRequest signUpRequest) {
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            return null;
        }
        User newUser = new User();
        newUser.setName(signUpRequest.getName());
        newUser.setSurname(signUpRequest.getSurname());
        newUser.setEmail(signUpRequest.getEmail());
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }
}
