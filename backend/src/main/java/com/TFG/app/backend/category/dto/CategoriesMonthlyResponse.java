package com.TFG.app.backend.category.dto;

import java.util.List;

public class CategoriesMonthlyResponse {
    List<CategoryResponse> categories;

    public CategoriesMonthlyResponse(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }
}
