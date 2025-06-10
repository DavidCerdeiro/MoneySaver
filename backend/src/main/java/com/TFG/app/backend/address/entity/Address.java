package com.TFG.app.backend.address.entity;

import jakarta.persistence.*;
import com.TFG.app.backend.street.entity.Street;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    
    @OneToOne
    @JoinColumn(name = "Id_Street", nullable = false, unique = true)
    private Street street;

    @Column(name = "Country", length = 16, nullable = false)
    private String country;

    @Column(name = "City", length = 16, nullable = false)
    private String city;

    //Getters and Setters
    public Integer getId() {
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
