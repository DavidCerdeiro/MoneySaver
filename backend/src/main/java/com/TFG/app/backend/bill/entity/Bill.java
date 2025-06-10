package com.TFG.app.backend.bill.entity;

import com.TFG.app.backend.spending.entity.Spending;
import jakarta.persistence.*;

@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @OneToOne
    @JoinColumn(name = "Id_Spending", nullable = false, unique = true)
    private Spending idSpending;

    @Column(name = "FileRoute", length = 256, nullable = false, unique = true)
    private String fileRoute;

    // Getters and Setters
    public Integer getId() {
        return Id;
    }

    public Spending getIdSpending() {
        return idSpending;
    }
    public void setIdSpending(Spending idSpending) {
        this.idSpending = idSpending;
    }

    public String getFileRoute() {
        return fileRoute;
    }
    public void setFileRoute(String fileRoute) {
        this.fileRoute = fileRoute;
    }

}
