package com.TFG.app.backend.purpose_otp.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "purpose_otp")
public class Purpose_OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name", length = 16, nullable = false, unique = true)
    private String name;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

