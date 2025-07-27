package com.TFG.app.backend.category.dto;

import java.util.List;

public class AllCategoriesFromUserResponse {
    private List<CategoryResponse> categories;

    public AllCategoriesFromUserResponse(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }
}
