package com.TFG.app.backend.establishment.entity;

import jakarta.persistence.*;
import com.TFG.app.backend.category.entity.Category;
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
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Category getId_category() {
        return id_category;
    }
    public void setId_category(Category id_category) {
        this.id_category = id_category;
    }
}
