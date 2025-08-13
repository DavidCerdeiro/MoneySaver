package com.TFG.app.backend.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"user\"")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name", length = 64, nullable = false)
    private String name;

    @Column(name = "Surname", length = 128, nullable = false)
    private String surname;

    @Column(name = "Email", length = 32, nullable = false, unique = true)
    private String email;

    @Column(name = "Password", length = 64, nullable = false)
    private String password;

    @Column(name = "IsAuthenticated", nullable = false)
    private boolean isAuthenticated = false;

    @Column(name = "FavouriteGraph")
    private Integer favouriteGraph = 1;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

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

    public boolean getIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public Integer getFavouriteGraph() {
        return favouriteGraph;
    }

    public void setFavouriteGraph(Integer favouriteGraph) {
        this.favouriteGraph = favouriteGraph;
    }
}