package com.TFG.app.backend.type_periodic.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type_periodic")
public class Type_Periodic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name", length = 16, nullable = false, unique = true)
    private String name;

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
}
