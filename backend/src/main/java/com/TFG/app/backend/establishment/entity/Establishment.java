package com.TFG.app.backend.establishment.entity;

import com.TFG.app.backend.user.entity.User;

import jakarta.persistence.*;
@Entity
@Table(name = "establishment")
public class Establishment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Id_User", nullable = false)
    private User user;

    @Column(name = "Name", length = 64, nullable = false, unique = true)
    private String name;

    public Establishment(Integer id, User user, String name) {
        this.id = id;
        this.user = user;
        this.name = name;
    }

    public Establishment() {}

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
