package com.TFG.app.backend.establishment.entity;

import jakarta.persistence.*;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.region.entity.Region;
@Entity
@Table(name = "establishment")
public class Establishment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name", length = 64, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "Id_Category", nullable = false)
    private Category category;

    @OneToOne
    @JoinColumn(name = "Id_Region", nullable = false)
    private Region region;

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

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public Region getRegion() {
        return region;
    }
    public void setRegion(Region region) {
        this.region = region;
    }
}
