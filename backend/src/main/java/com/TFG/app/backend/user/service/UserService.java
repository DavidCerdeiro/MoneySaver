package com.TFG.app.backend.user.service;

import com.TFG.app.backend.user.dto.LogInRequest;
import com.TFG.app.backend.user.dto.SignUpRequest;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.repository.UserRepository;

import com.TFG.app.backend.infraestructure.email.*;
import com.TFG.app.backend.infraestructure.one_time_password.entity.One_Time_Password;
import com.TFG.app.backend.infraestructure.one_time_password.service.One_Time_PasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Locale;
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

    /*
     * Method to reset a user's password.
     * It checks if the user exists by email.
     * If the user exists, it encodes the new password,
     * sets it for the user, and returns true.
     * If the user does not exist, it returns false.
     */
    public boolean resetPassword(String email, String newPassword, Locale locale) {
        Optional<User> user = userRepository.findByEmail(email);
        // Check if the user exists by email
        if(user.isPresent()) {
            User existingUser = user.get();
            // Encode the new password and set it for the user
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
            userRepository.save(existingUser);
             
            String subject = messageSource.getMessage("forgot.password.successSubject", null, locale);
            String body = messageSource.getMessage("forgot.password.successBody", null, locale);
            emailService.sendEmail(email, subject, body);
            
            return true;
        }
        return false;
    }

    /*
     * Method to send a forgot password email.
     * It checks if the user exists by email.
     * If the user exists, it generates a one-time password (OTP),
     * sends an email with the OTP,
     * and returns true.
     * If the user does not exist,
     * it returns false.
     */
    public boolean forgotPassword(String email, Locale locale) {
        Optional<User> user = userRepository.findByEmail(email);
        // Check if the user exists by email
        if(user.isPresent()) {
            // Generate a one-time password (OTP) and send it via email
            String otp = oneTimePasswordService.generateOTP(email, "forgotPassword");
            String subject = messageSource.getMessage("forgot.password.subject", null, locale);
            String body = messageSource.getMessage("forgot.password.body", new Object[]{otp}, locale);
            emailService.sendEmail(email, subject, body);

            return true;

        }
        return false;
    }

    /*
     * Method to authenticate a user.
     * It checks if the one-time password (OTP) exists for the given email.
     * If the OTP exists, it compares the provided code with the OTP token.
     * If they match, it updates the user's authentication status to true
     * and saves the user in the repository.
     * If the OTP does not exist or the code does not match,
     * it returns false, indicating the user is not authenticated.
     * If the user is authenticated successfully,
     * it returns true.
     */
    public boolean authUser(String email, String code, Locale locale) {
        One_Time_Password otp = oneTimePasswordService.getOTP(email);
        // Check if the OTP exists and if the provided code matches the OTP token
        if(otp != null) {
            if(otp.getToken().equals(code)) {
                oneTimePasswordService.deleteUsedOTP(otp.getId());
                Optional<User> user = userRepository.findByEmail(email);
                if(user.isPresent()) {
                    // Update the user's authentication status to true
                    User existingUser = user.get();
                    existingUser.setIsAuthenticated(true);
                    userRepository.save(existingUser);
                     
                    String subject = messageSource.getMessage("signUp.successSubject", null, locale);
                    String body = messageSource.getMessage("signUp.successBody", null, locale);
                    emailService.sendEmail(email, subject, body);
                    
                    return true;
                }
                
            }
        }
        return false;
    }

    /*
     * Method to verify a registered user.
     * It checks if the one-time password (OTP) exists for the given email.
     * If the OTP exists, it compares the provided code with the OTP token.
     * If they match, it returns true, indicating the user is verified.
     * If the OTP does not exist or the code does not match,
     * it returns false, indicating the user is not verified.
     */
    public boolean verifyUser(String email, String code, Locale locale) {
        One_Time_Password otp = oneTimePasswordService.getOTP(email);
        // Check if the OTP exists and if the provided code matches the OTP token
        boolean result = otp != null && otp.getToken().equals(code);
        // If the OTP is valid, delete it from the database
        if(result && otp != null){
            oneTimePasswordService.deleteUsedOTP(otp.getId());

            // Sending a success email to the user
            String subject = messageSource.getMessage("logIn.successSubject", null, locale);
            String body = messageSource.getMessage("logIn.successBody", null, locale);
            emailService.sendEmail(email, subject, body);
        }
        return result;
    }

    /*
     * Method to log in a user.
     * It checks if the user exists by email.
     * If the user exists, it verifies the password.
     * If the password matches, it generates a one-time password (OTP),
     * sends an email with the OTP,
     * and returns the user.
     * If the user does not exist or the password does not match,
     * it returns null.
     */
    public Optional<User> logInUser(LogInRequest logInRequest) {
        // Check if the user exists by email
        if(userRepository.findByEmail(logInRequest.getEmail()).isPresent()){
            Optional<User> user = userRepository.findByEmail(logInRequest.getEmail());
            // Verify the password
            if(passwordEncoder.matches(logInRequest.getPassword(), user.get().getPassword())) {
                // Generate a one-time password (OTP) and send it via email
                String otp = oneTimePasswordService.generateOTP(logInRequest.getEmail(), logInRequest.getPurpose());
                String subject = messageSource.getMessage("verify.email.subject", null, logInRequest.getLocale());
                String body = messageSource.getMessage("logIn.email.body", new Object[]{otp}, logInRequest.getLocale());
                emailService.sendEmail(logInRequest.getEmail(), subject, body);

                return user;
            }
        }
        return null;
        
    }

    /*
     * Method to create a new user.
     * It checks if the email already exists in the database.
     * If it does, it returns null.
     * If it doesn't, it creates a new User object,
     * encodes the password, generates a one-time password (OTP),
     * sends an email with the OTP,
     * and saves the new user to the database.
     */
    public User createUser(SignUpRequest signUpRequest) {
        // Check if the email already exists in the database
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            return null;
        }
        // Create a new User object and set its properties
        User newUser = new User();
        newUser.setName(signUpRequest.getName());
        newUser.setSurname(signUpRequest.getSurname());
        newUser.setEmail(signUpRequest.getEmail());
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        newUser.setPassword(encodedPassword);

        // Generate a one-time password (OTP) and send it via email
        String otp = oneTimePasswordService.generateOTP(signUpRequest.getEmail(), signUpRequest.getPurpose());
        String subject = messageSource.getMessage("verify.email.subject", null, signUpRequest.getLocale());
        String body = messageSource.getMessage("signUp.email.body", new Object[]{otp}, signUpRequest.getLocale());
        emailService.sendEmail(signUpRequest.getEmail(), subject, body);

        // Save the new user to the database
        return userRepository.save(newUser);
    }
}
