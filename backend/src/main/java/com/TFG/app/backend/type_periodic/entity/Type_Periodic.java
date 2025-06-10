package com.TFG.app.backend.type_periodic.entity;

import com.TFG.app.backend.type_periodic.enums.TypePeriodicEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "type_periodic")
public class Type_Periodic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "Name", length = 16, nullable = false, unique = true)
    private TypePeriodicEnum name;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public TypePeriodicEnum getName() {
        return name;
    }
}
