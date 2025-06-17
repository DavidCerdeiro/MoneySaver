package com.TFG.app.backend.user.dto;

import java.util.Locale;

/**
 * ForgotPasswordRequest class represents a request to initiate the forgot password process.
 */
public class ForgotPasswordRequest {
    public String email;

    public Locale locale;

    public String getEmail() {
        return email;
    }

    public Locale getLocale() {
        return locale;
    }
}
