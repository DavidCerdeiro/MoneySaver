package com.TFG.app.backend.category.dto;

import java.math.BigDecimal;
import com.TFG.app.backend.category.entity.Category;

public class CategoryResponse {
    private Integer id;
    private String name;
    private String icon;
    private BigDecimal totalSpending;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.icon = category.getIcon();
        this.totalSpending = category.getTotalSpending();
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getIcon() {
        return icon;
    }
    public BigDecimal getTotalSpending() {
        return totalSpending;
    }
}
