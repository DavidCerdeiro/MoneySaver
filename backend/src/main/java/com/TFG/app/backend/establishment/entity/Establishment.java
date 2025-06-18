package com.TFG.app.backend.establishment.entity;

import jakarta.persistence.*;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.address.entity.Address;
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
    @JoinColumn(name = "Id_Address", nullable = false, unique = true)
    private Address address;

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
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
}
