package com.TFG.app.backend.establishment.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "establishment")
public class Establishment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name", length = 32, nullable = false, unique = true)
    private String name;

    public Establishment(Integer id, String name) {
        this.id = id;
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

}
