package com.TFG.app.backend.spending.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
public class SpendingResponse {
    private Integer id;
    private String name;
    private BigDecimal amount;
    private LocalDate date;
    private String categoryName;
    private String iconCategory;
    private boolean isPeriodic;
    private String establishmentName;

    public SpendingResponse(Integer id, String name, BigDecimal amount, LocalDate date, String categoryName, String iconCategory, boolean isPeriodic, String establishmentName) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.categoryName = categoryName;
        this.iconCategory = iconCategory;
        this.isPeriodic = isPeriodic;
        this.establishmentName = establishmentName;
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
    public boolean isPeriodic() {
        return isPeriodic;
    }
    public String getEstablishmentName() {
        return establishmentName;
    }
}

