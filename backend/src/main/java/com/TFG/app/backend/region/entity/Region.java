package com.TFG.app.backend.region.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Country", length = 16, nullable = false)
    private String country;

    @Column(name = "City", length = 16, nullable = false)
    private String city;

    //Getters and Setters
    public Integer getId() {
        return id;
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
