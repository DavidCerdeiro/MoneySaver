package com.TFG.app.backend.user.dto;

/**
 * ResetPasswordRequest class represents the request object for resetting a user's password.
 */
public class ResetPasswordRequest {
    private String email;
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }
    public String getEmail() {
        return email;
    }
}
