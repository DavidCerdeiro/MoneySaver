package com.TFG.app.backend.user.dto;

import java.util.Locale;

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
