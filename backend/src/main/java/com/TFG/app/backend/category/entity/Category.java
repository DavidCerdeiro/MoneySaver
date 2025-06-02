package com.TFG.app.backend.category.entity;

import jakarta.persistence.*;
import com.TFG.app.backend.user.entity.User;

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User id_user;

    @Column(length = 32, nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Long totalSpending;

    @Column(nullable = false)
    private Long icon;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public User getId_user() {
        return id_user;
    }
    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getTotalSpending() {
        return totalSpending;
    }
    public void setTotalSpending(Long totalSpending) {
        this.totalSpending = totalSpending;
    }

    public Long getIcon() {
        return icon;
    }
    public void setIcon(Long icon) {
        this.icon = icon;
    }
}
