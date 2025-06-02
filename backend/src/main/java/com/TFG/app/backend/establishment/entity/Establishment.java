package com.TFG.app.backend.establishment.entity;

import jakarta.persistence.*;

@Entity
public class Establishment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category id_category;

    // Getters y setters

}
