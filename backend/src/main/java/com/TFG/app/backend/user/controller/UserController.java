package com.TFG.app.backend.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.dto.AuthUserRequest;
import com.TFG.app.backend.user.dto.ForgotPasswordRequest;
import com.TFG.app.backend.user.dto.ResetPasswordRequest;
import com.TFG.app.backend.user.dto.LogInRequest;
import com.TFG.app.backend.user.dto.SignUpRequest;
import com.TFG.app.backend.user.dto.UserResponse;
import com.TFG.app.backend.user.service.UserService;
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * Endpoint to log in a user.
     * @param logInRequest contains the email, password, and locale of the user.
     * @output:
     *          - 200 OK if the user is loged successfully.
     *          - 401 Unauthorized if the email or password is incorrect.
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponse> logInUser(@RequestBody LogInRequest logInRequest) {
        Optional<User> user = userService.logInUser(logInRequest);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);   
        }

        UserResponse userResponse = new UserResponse(user.get().getName(),  user.get().getSurname(), user.get().getEmail(), user.get().getIsAuthenticated());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
    
    /*
     * Endpoint to to send a email with a verification code to reset the password.
     * @param forgotPasswordRequest contains the email and locale of the user.
     * @output:
     *          - 200 OK if the email was sent successfully.
     *          - 401 Unauthorized if the email does not exist in the system.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        
        if (userService.forgotPassword(forgotPasswordRequest.getEmail(), forgotPasswordRequest.getLocale())) {
            return new ResponseEntity<>(HttpStatus.OK);   
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    /*
     * Endpoint to reset the password when the user has received the verification code in the email.
     * @param resetPasswordRequest contains the email and new password of the user.
     * @output:
     *         - 200 OK if the password was reset successfully.
     *         - 401 Unauthorized if the email does not exist in the system or the new password is invalid.
     */
    @PatchMapping("/forgot-password/reset")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {

        if (userService.resetPassword(resetPasswordRequest.getEmail(), resetPasswordRequest.getNewPassword())) {
            return new ResponseEntity<>(HttpStatus.OK);   
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    /*
     * Endpoint to authenticate a user with a verification code.
     * @param authUserRequest contains the email and code of the user.
     * @output:
     *         - 200 OK if the user is authenticated successfully.
     *         - 401 Unauthorized if the email does not exist in the system or the code is invalid.
     */
    @PatchMapping("/auth")
    public ResponseEntity<Void> authUser(@RequestBody AuthUserRequest authUserRequest) {
        
        if (userService.authUser(authUserRequest.getEmail(), authUserRequest.getCode())) {
            return new ResponseEntity<>(HttpStatus.OK);   
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /*
     * Endpoint to verify a registered user with a verification code.
     * @param authUserRequest contains the email and code of the user.
     * @output:
     *         - 200 OK if the user is verified successfully.
     *         - 401 Unauthorized if the email does not exist in the system or the code is invalid.
     */
    @PostMapping("/verify")
    public ResponseEntity<Void> verifyUser(@RequestBody AuthUserRequest authUserRequest) {
        if (userService.verifyUser(authUserRequest.getEmail(), authUserRequest.getCode())) {
            return new ResponseEntity<>(HttpStatus.OK);   
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /*
     * Endpoint to sign up a new user.
     * @param signUpRequest contains the name, surname, email, password, and locale of the user.
     * @output:
     *         - 201 Created if the user is created successfully.
     *         - 400 Bad Request if the user already exists or the input is invalid.
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody SignUpRequest signUpRequest) {
        User newUser = userService.createUser(signUpRequest);
        if(newUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserResponse userResponse = new UserResponse(newUser.getName(), newUser.getSurname(), newUser.getEmail(), newUser.getIsAuthenticated());
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}
