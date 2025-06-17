package com.TFG.app.backend.user.dto;

import java.util.Locale;
/*
 * SignUpRequest class represents a request to sign up a new user.
 */
public class SignUpRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    public Locale locale;
    public String purpose;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getPurpose() {
        return purpose;
    }
}
