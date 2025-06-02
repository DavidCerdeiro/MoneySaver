package com.TFG.app.backend.street.entity;

import jakarta.persistence.*;

public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(nullable = false)
    private Long number;

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getNumber() {
        return number;
    }
    public void setNumber(Long number) {
        this.number = number;
    }
}
