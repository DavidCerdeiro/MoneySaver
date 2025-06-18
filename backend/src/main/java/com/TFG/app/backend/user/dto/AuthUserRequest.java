package com.TFG.app.backend.user.dto;

import java.util.Locale;

/**
 * AuthUserRequest class represents a request to authenticate a user using an email and a code.
 */
public class AuthUserRequest {
    private String email;

    private String code;

    private Locale locale;

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public Locale getLocale() {
        return locale;
    }
}
