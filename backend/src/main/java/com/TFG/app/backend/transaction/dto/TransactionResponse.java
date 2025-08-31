package com.TFG.app.backend.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionResponse {
    private Integer id;
    private String name;
    private BigDecimal amount;
    private LocalDate date;
    private String accountName;
    private String accountNumber;
    private String categoryIcon;
    private String categoryName;
    private String establishmentName;

    public TransactionResponse(Integer id, String name, BigDecimal amount, LocalDate date, String accountName, String accountNumber, String categoryIcon, String categoryName, String establishmentName) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.categoryIcon = categoryIcon;
        this.categoryName = categoryName;
        this.establishmentName = establishmentName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

}
