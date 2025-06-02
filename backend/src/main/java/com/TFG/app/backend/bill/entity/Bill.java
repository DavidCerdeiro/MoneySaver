package com.TFG.app.backend.bill.entity;

import jakarta.persistence.*;

public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne
    @JoinColumn(name = "id_spending", nullable = false, unique = true)
    private Long idSpending;

    @Column(length = 256, nullable = false, unique = true)
    private String fileRoute;

    // Getters and Setters
    public Long getId() {
        return Id;
    }

    public Long getIdSpending() {
        return idSpending;
    }
    public void setIdSpending(Long idSpending) {
        this.idSpending = idSpending;
    }

    public String getFileRoute() {
        return fileRoute;
    }
    public void setFileRoute(String fileRoute) {
        this.fileRoute = fileRoute;
    }

}
