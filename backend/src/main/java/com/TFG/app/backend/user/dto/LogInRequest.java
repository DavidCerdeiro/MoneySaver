package com.TFG.app.backend.user.dto;

import java.util.Locale;

/*
 * LogInRequest class represents a request to log in a user.
 */
public class LogInRequest {
    private String email;
    private String password;
    private Locale locale;
    public String purpose;

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Locale getLocale() {
        return locale;
    }
    public String getPurpose() {
        return purpose;
    }
}
