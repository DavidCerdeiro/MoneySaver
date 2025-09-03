package com.TFG.app.backend.spending.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.establishment.entity.Establishment;

@Entity
@Table(name = "spending")
public class Spending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Id_Category", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "Id_Establishment")
    private Establishment establishment;

    @Column(name = "Name", length = 32, nullable = false)
    private String name;

    @Column(name = "Amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Column(name = "IsPeriodic", nullable = false)
    private Boolean isPeriodic = false;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public Establishment getEstablishment() {
        return establishment;
    }
    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getIsPeriodic() {
        return isPeriodic;
    }
    public void setIsPeriodic(Boolean isPeriodic) {
        this.isPeriodic = isPeriodic;
    }

}
