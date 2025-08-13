package com.TFG.app.backend.user.dto;

public class ProfileResponse {
    private Integer id;
    private String name;
    private String surname;
    private String email;


    public ProfileResponse(Integer id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
}
