package com.TFG.app.backend.spending.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SpendingResponse {
    private Integer id;
    private String name;
    private BigDecimal amount;
    private Date date;
    private String categoryName;
    private String iconCategory;
    private boolean isPeriodic;

    public SpendingResponse(Integer id, String name, BigDecimal amount, Date date, String categoryName, String iconCategory, boolean isPeriodic) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.categoryName = categoryName;
        this.iconCategory = iconCategory;
        this.isPeriodic = isPeriodic;
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
    public Date getDate() {
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
}

