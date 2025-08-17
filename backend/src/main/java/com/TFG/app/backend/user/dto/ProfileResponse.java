package com.TFG.app.backend.user.dto;

public class ProfileResponse {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private Integer idTypeChart;

    public ProfileResponse(Integer id, String name, String surname, String email, Integer idTypeChart) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.idTypeChart = idTypeChart;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public Integer getIdTypeChart() { return idTypeChart; }
}
