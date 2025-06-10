package com.TFG.app.backend.periodic_spending.entity;

import jakarta.persistence.*;
import java.util.Date;

import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.type_periodic.entity.Type_Periodic;

@Entity
@Table(name = "periodic_spending")
public class Periodic_Spending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "Id_Spending", unique = true, nullable = false)
    private Spending spending;

    @ManyToOne
    @JoinColumn(name = "Id_TypePeriodic", nullable = false)
    private Type_Periodic typePeriodic;

    @Column(name = "Expiration", nullable = false)
    private Date expiration;
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public Spending getSpending() {
        return spending;
    }
    public void setSpending(Spending spending) {
        this.spending = spending;
    }

    public Type_Periodic getTypePeriodic() {
        return typePeriodic;
    }
    public void setTypePeriodic(Type_Periodic typePeriodic) {
        this.typePeriodic = typePeriodic;
    }

    public Date getExpiration() {
        return expiration;
    }
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
