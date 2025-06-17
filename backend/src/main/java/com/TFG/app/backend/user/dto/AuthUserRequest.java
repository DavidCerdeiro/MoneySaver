package com.TFG.app.backend.user.dto;

/**
 * AuthUserRequest class represents a request to authenticate a user using an email and a code.
 */
public class AuthUserRequest {
    private String email;

    private String code;

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }
}
