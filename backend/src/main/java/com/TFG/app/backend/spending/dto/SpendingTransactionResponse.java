package com.TFG.app.backend.spending.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.TFG.app.backend.establishment.entity.Establishment;

public class SpendingTransactionResponse {
    private BigDecimal amount;
    private String name;
    private Establishment establishment;
    private LocalDate date;
    private Integer idCategory;

    public SpendingTransactionResponse() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getIdCategory() {
        return idCategory;
    }
    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }
}
