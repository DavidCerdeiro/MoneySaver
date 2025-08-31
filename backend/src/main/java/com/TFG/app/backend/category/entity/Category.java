package com.TFG.app.backend.category.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

import com.TFG.app.backend.user.entity.User;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Id_User", nullable = false)
    private User user;

    @Column(name = "Name", length = 32, nullable = false)
    private String name;

    @Column(name = "Icon", length = 64, nullable = false)
    private String icon;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDate createdAt;

    @Column(name = "DeletedAt", nullable = true)
    private LocalDate deletedAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDate.now().withDayOfMonth(1);
        }
    }
    // Getters and Setters
    public Integer  getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }
}
