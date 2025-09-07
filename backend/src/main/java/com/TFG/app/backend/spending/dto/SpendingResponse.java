package com.TFG.app.backend.spending.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.TFG.app.backend.bill.entity.Bill;
import com.TFG.app.backend.spending.entity.Spending;
public class SpendingResponse {
 
    private Integer id;
    private String name;
    private BigDecimal amount;
    private LocalDate date;
    private String categoryName;
    private String iconCategory;
    private String establishmentName;
    private Integer billId;

    public SpendingResponse(Integer id, String name, BigDecimal amount, LocalDate date, String categoryName, String iconCategory, String establishmentName, Integer billId) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.categoryName = categoryName;
        this.billId = billId;
        this.iconCategory = iconCategory;
        this.establishmentName = establishmentName;
    }

    public SpendingResponse(Spending spending, Bill bill) {
        this.id = spending.getId();
        this.name = spending.getName();
        this.amount = spending.getAmount();
        this.date = spending.getDate();
        this.categoryName = spending.getCategory().getName();
        this.iconCategory = spending.getCategory().getIcon();
        this.establishmentName = spending.getEstablishment().getName();
        this.billId = (bill != null) ? bill.getId() : null;
    }
    public Integer getId() {
        return id;
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
    public String getCategoryName() {
        return categoryName;
    }
    public String getIconCategory() {
        return iconCategory;
    }
    public String getEstablishmentName() {
        return establishmentName;
    }
    public Integer getBillId() {
        return billId;
    }
}

