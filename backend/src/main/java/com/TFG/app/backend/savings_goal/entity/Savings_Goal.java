package com.TFG.app.backend.savings_goal.entity;

import jakarta.persistence.*;

import com.TFG.app.backend.category.entity.Category;

public class Savings_Goal {
    /*"Id" serial  PRIMARY KEY,
  "Id_Category" int NOT NULL,
  "Name" varchar(32) NOT NULL UNIQUE,
  "TargetAmount" numeric(15,2) NOT NULL,
  FOREIGN KEY ("Id_Category") REFERENCES category("Id") */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;

    @Column(length = 32, nullable = false, unique = true)
    private String name;

    @Column(nullable = false, precision = 15, scale = 2)
    private Double targetAmount;

    // Getters and Setters
    public Long getId() {
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

    public Double getTargetAmount() {
        return targetAmount;
    }
    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }
}
