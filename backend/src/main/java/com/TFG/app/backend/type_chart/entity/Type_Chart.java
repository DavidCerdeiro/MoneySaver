package com.TFG.app.backend.type_chart.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type_chart")
public class Type_Chart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name", length = 8, nullable = false, unique = true)
    private String name;

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