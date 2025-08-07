package com.TFG.app.backend.goal.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.TFG.app.backend.category.entity.Category;

@Entity
@Table(name = "goal")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "Id_Category", nullable = false)
    private Category category;

    @Column(name = "Name", length = 32, nullable = false, unique = true)
    private String name;

    @Column(name = "TargetAmount", nullable = false, precision = 15, scale = 2)
    private BigDecimal targetAmount;

    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }
    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount.setScale(2, RoundingMode.HALF_UP);
    }
}
