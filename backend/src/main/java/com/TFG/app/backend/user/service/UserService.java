package com.TFG.app.backend.user.service;

import com.TFG.app.backend.user.dto.LogInRequest;
import com.TFG.app.backend.user.dto.SignUpRequest;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.repository.UserRepository;

import com.TFG.app.backend.infraestructure.email.*;
import com.TFG.app.backend.infraestructure.one_time_password.service.One_Time_PasswordService;
import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.service.Type_ChartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Locale;
import java.util.Optional;
import com.google.common.cache.Cache;

import jakarta.transaction.Transactional;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final One_Time_PasswordService oneTimePasswordService;
    private final EmailService emailService;
    private final Type_ChartService typeChartService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Cache<String, String> otpCache;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, One_Time_PasswordService oneTimePasswordService, EmailService emailService, Type_ChartService typeChartService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.oneTimePasswordService = oneTimePasswordService;
        this.emailService = emailService;
        this.typeChartService = typeChartService;
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
            String otp = oneTimePasswordService.generateOTP(email);
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
     * it returns the user ID
     */
    public Integer authUser(String email, String code, Locale locale) {
        String otp = oneTimePasswordService.getOTP(email);
        // Check if the OTP exists and if the provided code matches the OTP token
        if(otp != null) {
            if(otp.equals(code)) {
                otpCache.invalidate(email); // Invalidate the OTP after successful authentication
                Optional<User> user = userRepository.findByEmail(email);
                if(user.isPresent()) {
                    // Update the user's authentication status to true
                    User existingUser = user.get();
                    existingUser.setIsAuthenticated(true);
                    userRepository.save(existingUser);
                     
                    String subject = messageSource.getMessage("signUp.successSubject", null, locale);
                    String body = messageSource.getMessage("signUp.successBody", null, locale);
                    emailService.sendEmail(email, subject, body);

                    return existingUser.getId();
                }
                
            }
        }
        return null;
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
        String otp = oneTimePasswordService.getOTP(email);
        // Check if the OTP exists and if the provided code matches the OTP token
        boolean result = otp != null && otp.equals(code);
        // If the OTP is valid, delete it from the database
        if(result && otp != null){
            otpCache.invalidate(email); // Invalidate the OTP after successful verification

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
            System.out.println("User found: " + logInRequest.getEmail());
            Optional<User> user = userRepository.findByEmail(logInRequest.getEmail());
            // Verify the password
            System.out.println("Password to verify: " + logInRequest.getPassword());
            if(passwordEncoder.matches(logInRequest.getPassword(), user.get().getPassword())) {
                System.out.println("Password matches for user: " + logInRequest.getEmail());
                // Generate a one-time password (OTP) and send it via email
                String otp = oneTimePasswordService.generateOTP(logInRequest.getEmail());
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
        newUser.setTypeChart(typeChartService.getTypeChartById(1));
        // Generate a one-time password (OTP) and send it via email
        String otp = oneTimePasswordService.generateOTP(signUpRequest.getEmail());
        String subject = messageSource.getMessage("verify.email.subject", null, signUpRequest.getLocale());
        String body = messageSource.getMessage("signUp.email.body", new Object[]{otp}, signUpRequest.getLocale());
        emailService.sendEmail(signUpRequest.getEmail(), subject, body);

        // Save the new user to the database
        return userRepository.save(newUser);
    }

    /*
     * Method to get a user by ID.
     * It retrieves the user from the repository by ID.
     * If the user exists, it returns the user object.
     * If the user does not exist, it returns null.
     */
    public User getUserById(Integer id) {
        // Retrieve the user by ID from the repository
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * Method to send a verification email for account deletion.
     * It checks if the user exists by email.
     * If it does, it generates a one-time password (OTP) and sends it via email.
     * If it doesn't, it returns false.
     */
    public boolean sendDeleteVerification(String email, Locale locale) {
        Optional<User> user = userRepository.findByEmail(email);
        // Check if the user exists by email
        if(user.isPresent()) {
            // Generate a one-time password (OTP) and send it via email
            String otp = oneTimePasswordService.generateOTP(email);
            String subject = messageSource.getMessage("delete.email.subject", null, locale);
            String body = messageSource.getMessage("delete.email.body", new Object[]{otp}, locale);
            emailService.sendEmail(email, subject, body);

            return true;

        }
        return false;
    }

    /*
     * Method to delete a user.
     * It checks if the user is not null.
     * If it is not, it deletes the user from the repository.
     * If it is null, it does nothing.
     */
    @Transactional
    public boolean deleteUser(User user) {
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    /*
     * Method to modify a user.
     * It updates the user's properties that are modified.
     * If it doesn't, it does nothing.
     */
    public void modifyUser(User user, String name, String surname, String password, Integer idTypeChart) {
        if(user.getName() != name) {
            user.setName(name);
        }
        if(user.getSurname() != surname) {
            user.setSurname(surname);
        }
    
        if(password != "" && !passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("PASSWORD CHANGED");
            user.setPassword(passwordEncoder.encode(password));
        }
        
        if(idTypeChart != null) {
            Type_Chart favouriteTypeChart = typeChartService.getTypeChartById(idTypeChart);
            if(favouriteTypeChart != user.getTypeChart()) {
                user.setTypeChart(favouriteTypeChart);
            }
        }

        userRepository.save(user);
    }

    public Integer getFavouriteTypeChart(User user) {
        if (user != null) {
            return user.getTypeChart().getId();
        }
        return null;
    }
}
