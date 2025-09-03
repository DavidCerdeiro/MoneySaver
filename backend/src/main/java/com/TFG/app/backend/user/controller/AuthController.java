package com.TFG.app.backend.user.controller;

import com.TFG.app.backend.infraestructure.config.CookieUtil;
import com.TFG.app.backend.infraestructure.config.JwtService;
import com.TFG.app.backend.user.dto.AuthUserRequest;
import com.TFG.app.backend.user.dto.LogInRequest;
import com.TFG.app.backend.user.dto.UserResponse;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
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
    @PostMapping("/sessions")
    public ResponseEntity<UserResponse> logInUser(@RequestBody LogInRequest logInRequest) {
        Optional<User> user = userService.logInUser(logInRequest);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);   
        }
        
        UserResponse userResponse = new UserResponse(user.get().getId(), user.get().getName(), user.get().getSurname(), user.get().getEmail(), user.get().getIsAuthenticated());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    

    @DeleteMapping("/sessions")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/sessions/me")
    public ResponseEntity<Void> checkAuth(@CookieValue(name = "accessToken", required = false) String token) {
        if (token != null && jwtService.validateToken(token)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Endpoint to refresh the access token.
     * @param refreshToken the refresh token of the user.
     * @output:
     *         - 200 OK if the access token is refreshed successfully.
     *         - 401 Unauthorized if the refresh token is invalid or expired.
     */
    @PostMapping("/sessions/refresh")
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
        ResponseCookie refreshCookie = CookieUtil.createHttpOnlyCookie("refreshToken", newRefreshToken, 3600); // 1 hora

        // Add cookies to the response
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok(Map.of("status", "refreshed"));
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
}
