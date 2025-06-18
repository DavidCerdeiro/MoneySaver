package com.TFG.app.backend.user.dto;

import java.util.Locale;

/**
 * ResetPasswordRequest class represents the request object for resetting a user's password.
 */
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
    private Locale locale;
    public String getNewPassword() {
        return newPassword;
    }
    public String getEmail() {
        return email;
    }
    public Locale getLocale() {
        return locale;
    }
}
