package com.TFG.app.backend.infraestructure.config;

import org.springframework.http.ResponseCookie;

public class CookieUtil {

    public static ResponseCookie createHttpOnlyCookie(String name, String value, long maxAgeSeconds) {
        // Create a HttpOnly cookie
        return ResponseCookie.from(name, value)
                .httpOnly(true) // Set HttpOnly flag
                .secure(true) // Set Secure flag
                .path("/") // Set cookie path
                .sameSite("Strict") // Set SameSite attribute
                .maxAge(maxAgeSeconds) // Set cookie max age
                .build();
    }
}
