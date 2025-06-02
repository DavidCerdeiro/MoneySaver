package com.TFG.app.backend.type_periodic.entity;

import com.TFG.app.backend.type_periodic.enums.TypePeriodicEnum;
import jakarta.persistence.*;

@Entity
public class Type_Periodic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 16, nullable = false, unique = true)
    private TypePeriodicEnum name;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public TypePeriodicEnum getName() {
        return name;
    }
}
