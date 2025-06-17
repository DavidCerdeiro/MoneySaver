package com.TFG.app.backend.user.dto;

/**
 * UserResponse class represents the response object for user-related operations.
 */
public class UserResponse {
    private String name;
    private String surname;
    private String email;
    private boolean isAuthenticated;

    public UserResponse(String name, String surname, String email, boolean isAuthenticated) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.isAuthenticated = isAuthenticated;
    }

    //Getters and Setters
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
    public boolean getIsAuthenticated() {
        return isAuthenticated;
    }
    public void setIsAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

}
