package com.TFG.app.backend.address.entity;

import jakarta.persistence.*;
import com.TFG.app.backend.street.entity.Street;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "id_street", nullable = false, unique = true)
    private Street street;

    @Column(length = 16, nullable = false)
    private String country;

    @Column(length = 16, nullable = false)
    private String city;

    //Getters and Setters
    public Long getId() {
        return id;
    }
    
    public Street getStreet() {
        return street;
    }
    public void setStreet(Street street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}
