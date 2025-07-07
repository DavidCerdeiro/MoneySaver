package com.TFG.app.backend.category.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    @Column(name = "TotalSpending", nullable = false, precision = 15, scale = 2)
    private BigDecimal  totalSpending = BigDecimal.ZERO;

    @Column(name = "Icon", length = 64, nullable = false)
    private String icon;

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
        this.totalSpending = totalSpending.setScale(2, RoundingMode.HALF_UP);
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
}
