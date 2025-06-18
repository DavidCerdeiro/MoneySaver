package com.TFG.app.backend.spending.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.user.entity.User;

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

    @ManyToOne
    @JoinColumn(name = "Id_User", nullable = false)
    private User user;

    @Column(name = "Name", length = 64, nullable = false)
    private String name;

    @Column(name = "Amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "Date", nullable = false)
    private Date date;

    @Column(name = "IsPeriodic", nullable = false)
    private Boolean isPeriodic;

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

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
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
