package com.TFG.app.backend.infraestructure.config;

import org.springframework.http.ResponseCookie;
import java.util.Arrays;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;

public class CookieUtil {

    public static ResponseCookie createHttpOnlyCookie(String name, String value, long maxAgeSeconds) {
        // Create a HttpOnly cookie
        return ResponseCookie.from(name, value)
                .httpOnly(true) // Set HttpOnly flag
                .secure(true) // Set Secure flag
                .path("/") // Set cookie path
                .sameSite("None") // Allow cross-site requests
                .maxAge(maxAgeSeconds) // Set cookie max age
                .build();
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(c -> name.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
