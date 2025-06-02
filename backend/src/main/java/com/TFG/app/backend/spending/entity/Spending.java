package com.TFG.app.backend.spending.entity;

import jakarta.persistence.*;
import java.util.Date;

import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.user.entity.User;

public class Spending {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_establishment")
    private Establishment establishment;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(length = 64, nullable = false)
    private String name;

    @Column(nullable = false, precision = 15, scale = 2)
    private Double amount;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Boolean isPeriodic;

    // Getters and Setters
    public Long getId() {
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

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getIsPeriodic() {
        return isPeriodic;
    }
    public void setIsPeriodic(Boolean isPeriodic) {
        this.isPeriodic = isPeriodic;
    }

}
