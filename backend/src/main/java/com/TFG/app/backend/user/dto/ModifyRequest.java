package com.TFG.app.backend.user.dto;

public class ModifyRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Integer idTypeChart;

    // Getters and Setters
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

    public Integer getIdTypeChart() {
        return idTypeChart;
    }

    public void setIdTypeChart(Integer idTypeChart) {
        this.idTypeChart = idTypeChart;
    }
}
