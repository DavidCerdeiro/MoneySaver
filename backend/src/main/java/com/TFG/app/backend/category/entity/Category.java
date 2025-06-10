package com.TFG.app.backend.category.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

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

    @Column(name = "Name", length = 32, nullable = false, unique = true)
    private String name;

    @Column(name = "TotalSpending", nullable = false)
    private BigDecimal  totalSpending;

    @Column(name = "Icon", nullable = false)
    private Integer icon;

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

    public BigDecimal  getTotalSpending() {
        return totalSpending;
    }
    public void setTotalSpending(BigDecimal  totalSpending) {
        this.totalSpending = totalSpending;
    }

    public Integer getIcon() {
        return icon;
    }
    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
