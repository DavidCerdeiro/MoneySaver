package com.TFG.app.backend.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.infraestructure.config.CookieUtil;
import com.TFG.app.backend.infraestructure.config.JwtService;
import com.TFG.app.backend.user.dto.AuthUserRequest;
import com.TFG.app.backend.user.dto.ForgotPasswordRequest;
import com.TFG.app.backend.user.dto.ResetPasswordRequest;
import com.TFG.app.backend.user.dto.LogInRequest;
import com.TFG.app.backend.user.dto.ModifyRequest;
import com.TFG.app.backend.user.dto.ProfileResponse;
import com.TFG.app.backend.user.dto.SignUpRequest;
import com.TFG.app.backend.user.dto.UserResponse;
import com.TFG.app.backend.user.service.UserService;
import org.springframework.http.HttpHeaders;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;




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
        
        UserResponse userResponse = new UserResponse(user.get().getId(), user.get().getName(), user.get().getSurname(), user.get().getEmail(), user.get().getIsAuthenticated());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
    
    /**
     * Endpoint to to send a email with a verification code to reset the password.
     * @param forgotPasswordRequest contains the email and locale of the user.
     * @output:
     *          - 200 OK if the email was sent successfully.
     *          - 401 Unauthorized if the email does not exist in the system.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        
        if (userService.forgotPassword(forgotPasswordRequest.getEmail(), forgotPasswordRequest.getLocale())) {
            System.out.println("HOLA");
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
    @PatchMapping("/forgot-password/reset")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {

        if (userService.resetPassword(resetPasswordRequest.getEmail(), resetPasswordRequest.getNewPassword(), resetPasswordRequest.getLocale())) {
            return new ResponseEntity<>(HttpStatus.OK);   
        }
        
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    /**
     * Endpoint to authenticate a user with a verification code.
     * @param authUserRequest contains the email and code of the user.
     * @output:
     *         - 200 OK if the user is authenticated successfully.
     *         - 401 Unauthorized if the email does not exist in the system or the code is invalid.
     */
    @PatchMapping("/auth")
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

    /**
     * Endpoint to refresh the access token.
     * @param refreshToken the refresh token of the user.
     * @output:
     *         - 200 OK if the access token is refreshed successfully.
     *         - 401 Unauthorized if the refresh token is invalid or expired.
     */
    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Validate refresh token
        if (!jwtService.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        // Extract email from the refresh token
        String email = jwtService.getEmailFromToken(refreshToken);
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Generate new tokens
        String newAccessToken = jwtService.generateAccessToken(email);
        String newRefreshToken = jwtService.generateRefreshToken(email);

        // Create HttpOnly and Secure cookies for the tokens
        ResponseCookie accessCookie = CookieUtil.createHttpOnlyCookie("accessToken", newAccessToken, 900); // 15 min
        ResponseCookie refreshCookie = CookieUtil.createHttpOnlyCookie("refreshToken", newRefreshToken, 7 * 24 * 60 * 60); // 7 días

        // Add cookies to the response
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok(Map.of("status", "refreshed"));
    }

    /**
     * Endpoint to verify a registered user with a verification code.
     * @param authUserRequest contains the email and code of the user.
     * @output:
     *         - 200 OK if the user is verified successfully.
     *         - 401 Unauthorized if the email does not exist in the system or the code is invalid.
     */
    @PostMapping("/verify")
    public ResponseEntity<Void> verifyUser(@RequestBody AuthUserRequest authUserRequest,
                                          HttpServletResponse response) {

        if (userService.verifyUser(authUserRequest.getEmail(),
                                authUserRequest.getCode(),
                                authUserRequest.getLocale())) {

            // 1. Generate tokens
            String accessToken = jwtService.generateAccessToken(authUserRequest.getEmail());
            String refreshToken = jwtService.generateRefreshToken(authUserRequest.getEmail());

            // 2. Create secure cookies
            ResponseCookie accessCookie = CookieUtil.createHttpOnlyCookie("accessToken", accessToken, 900);
            ResponseCookie refreshCookie = CookieUtil.createHttpOnlyCookie("refreshToken", refreshToken, 7 * 24 * 60 * 60);

            // 3. Add cookies
            response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
            response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    /**
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

        UserResponse userResponse = new UserResponse(newUser.getId(), newUser.getName(), newUser.getSurname(), newUser.getEmail(), newUser.getIsAuthenticated());
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    /**
     * Endpoint to get the profile of the logged-in user.
     * @param token the access token of the user.
     * @output:
     *         - 200 OK if the profile is retrieved successfully.
     *         - 401 Unauthorized if the token is invalid or expired.
     *         - 404 Not Found if the user does not exist.
     */
    @GetMapping("/getProfile")
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
    @PostMapping("/verification-email/delete")
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
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@CookieValue(name = "accessToken", required = false) String token) {

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
        return ResponseEntity.ok(Map.of("status", "deleted"));
    }

    /**
     * Endpoint to modify user profile.
     * @param token the access token of the user.
     * @param modifyRequest the request body containing the modified user data.
     * @output:
     *         - 200 OK if the profile is modified successfully.
     *         - 401 Unauthorized if the token is invalid or expired.
     *         - 404 Not Found if the user does not exist.
     */
    @PutMapping("modify")
    public ResponseEntity<?> modifyProfile(@CookieValue(name = "accessToken", required = false) String token, @RequestBody ModifyRequest modifyRequest) {

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

        userService.modifyUser(user, modifyRequest.getName(), modifyRequest.getSurname(),modifyRequest.getPassword(), modifyRequest.getIdTypeChart());
        return ResponseEntity.ok(Map.of("status", "modified"));
    }

    /**
     * Endpoint to get the user's favorite type chart.
     * @param token
     * @output:
     *         - 200 OK with the user's favorite type chart if successful.
     *         - 401 Unauthorized if the token is invalid or expired.
     *         - 404 Not Found if the user does not exist.
     */
    @GetMapping("/get-type-chart")
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
    
}
