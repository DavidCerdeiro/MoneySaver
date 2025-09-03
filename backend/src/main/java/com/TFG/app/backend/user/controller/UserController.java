package com.TFG.app.backend.user.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;
import java.util.Map;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.infraestructure.config.CookieUtil;
import com.TFG.app.backend.infraestructure.config.JwtService;
import com.TFG.app.backend.user.dto.AuthUserRequest;
import com.TFG.app.backend.user.dto.ForgotPasswordRequest;
import com.TFG.app.backend.user.dto.ResetPasswordRequest;
import com.TFG.app.backend.user.dto.SignUpRequest;
import com.TFG.app.backend.user.dto.UserResponse;
import com.TFG.app.backend.user.dto.ModifyRequest;
import com.TFG.app.backend.user.dto.ProfileResponse;
import com.TFG.app.backend.user.service.UserService;

import jakarta.servlet.http.HttpServletResponse;




@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }
    
    /**
     * Endpoint to sign up a new user.
     * @param signUpRequest contains the name, surname, email, password, and locale of the user.
     * @output:
     *         - 201 Created if the user is created successfully.
     *         - 400 Bad Request if the user already exists or the input is invalid.
     */
    @PostMapping
    public ResponseEntity<UserResponse> signup(@RequestBody SignUpRequest signUpRequest) {
        
        User newUser = userService.createUser(signUpRequest);
        if(newUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserResponse userResponse = new UserResponse(newUser.getId(), newUser.getName(), newUser.getSurname(), newUser.getEmail(), newUser.getIsAuthenticated());
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
    /**
     * Endpoint to to send a email with a verification code to reset the password.
     * @param forgotPasswordRequest contains the email and locale of the user.
     * @output:
     *          - 200 OK if the email was sent successfully.
     *          - 401 Unauthorized if the email does not exist in the system.
     */
    @PostMapping("/password-reset")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        
        if (userService.forgotPassword(forgotPasswordRequest.getEmail(), forgotPasswordRequest.getLocale())) {
            return new ResponseEntity<>(HttpStatus.OK);   
        }
        
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Endpoint to reset the password when the user has received the verification code in the email.
     * @param resetPasswordRequest contains the email and new password of the user.
     * @output:
     *         - 200 OK if the password was reset successfully.
     *         - 401 Unauthorized if the email does not exist in the system or the new password is invalid.
     */
    @PatchMapping("/password-reset")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {

        if (userService.resetPassword(resetPasswordRequest.getEmail(), resetPasswordRequest.getNewPassword(), resetPasswordRequest.getLocale())) {
            return new ResponseEntity<>(HttpStatus.OK);   
        }
        
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Endpoint to get the profile of the logged-in user.
     * @param token the access token of the user.
     * @output:
     *         - 200 OK if the profile is retrieved successfully.
     *         - 401 Unauthorized if the token is invalid or expired.
     *         - 404 Not Found if the user does not exist.
     */
    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getProfile(@CookieValue(name = "accessToken", required = false) String token) {
        
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProfileResponse profileResponse = new ProfileResponse(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getTypeChart().getId());
        return new ResponseEntity<>(profileResponse, HttpStatus.OK);
    }

    /**
     * Endpoint to send a verification email for account deletion.
     * @param token the access token of the user.
     * @output:
     *         - 200 OK if the email is sent successfully.
     *         - 401 Unauthorized if the token is invalid or expired.
     *         - 404 Not Found if the user does not exist.
     */
    @PostMapping("/verification")
    public ResponseEntity<?> verificationEmailToDelete(@CookieValue(name = "accessToken", required = false) String token, Locale locale) {

        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Send verification email to delete the account
        boolean emailSent = userService.sendDeleteVerification(email, locale);
        if (!emailSent) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(Map.of("status", "sent"));
    }

    /**
     * Endpoint to delete a user account.
     * @param token the access token of the user.
     * @output:
     *         - 200 OK if the account is deleted successfully.
     *         - 401 Unauthorized if the token is invalid or expired.
     *         - 404 Not Found if the user does not exist.
     */
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteProfile(@CookieValue(name = "accessToken", required = false) String token) {

        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint to edit user profile.
     * @param token the access token of the user.
     * @param modifyRequest the request body containing the modified user data.
     * @output:
     *         - 200 OK if the profile is modified successfully.
     *         - 401 Unauthorized if the token is invalid or expired.
     *         - 404 Not Found if the user does not exist.
     */
    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> editProfile(@CookieValue(name = "accessToken", required = false) String token, @RequestBody ModifyRequest modifyRequest) {

        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User newUser = userService.modifyUser(user, modifyRequest.getName(), modifyRequest.getSurname(),modifyRequest.getPassword(), modifyRequest.getIdTypeChart());
        ProfileResponse profileResponse = new ProfileResponse(newUser.getId(), newUser.getName(), newUser.getSurname(), newUser.getEmail(), newUser.getTypeChart().getId());
        return ResponseEntity.ok(profileResponse);
    }

    /**
     * Endpoint to get the user's favorite type chart.
     * @param token
     * @output:
     *         - 200 OK with the user's favorite type chart if successful.
     *         - 401 Unauthorized if the token is invalid or expired.
     *         - 404 Not Found if the user does not exist.
     */
    @GetMapping("/me/type-chart")
    public ResponseEntity<Integer> getTypeChart(@CookieValue(name = "accessToken", required = false) String token) {

        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Integer favouriteTypeChart = userService.getFavouriteTypeChart(user);
        return new ResponseEntity<>(favouriteTypeChart, HttpStatus.OK);
    }
    
    /**
     * Endpoint to authenticate a user with a verification code.
     * @param authUserRequest contains the email and code of the user.
     * @output:
     *         - 200 OK if the user is authenticated successfully.
     *         - 401 Unauthorized if the email does not exist in the system or the code is invalid.
     */
    @PatchMapping("/authenticate")
    public ResponseEntity<Void> authUser(@RequestBody AuthUserRequest authUserRequest,
                                            HttpServletResponse response) {
        Integer userID = userService.authUser(authUserRequest.getEmail(),
                                            authUserRequest.getCode(),
                                            authUserRequest.getLocale());

        if (userID != null) {
            // Generate new tokens
            String accessToken = jwtService.generateAccessToken(authUserRequest.getEmail());
            String refreshToken = jwtService.generateRefreshToken(authUserRequest.getEmail());

            // Create secure cookies
            ResponseCookie accessCookie = CookieUtil.createHttpOnlyCookie("accessToken", accessToken, 900); // 15 min
            ResponseCookie refreshCookie = CookieUtil.createHttpOnlyCookie("refreshToken", refreshToken, 7 * 24 * 60 * 60); // 7 days

            // Add cookies to the response
            response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
            response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
