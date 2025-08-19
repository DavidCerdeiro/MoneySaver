package com.TFG.app.backend.category.dto;

import java.util.List;

public class CompareMonthsResponse {
    private List<CategoryResponse> month1;
    private List<CategoryResponse> month2;

    public CompareMonthsResponse(List<CategoryResponse> month1, List<CategoryResponse> month2) {
        this.month1 = month1;
        this.month2 = month2;
    }

    public List<CategoryResponse> getMonth1() {
        return month1;
    }

    public List<CategoryResponse> getMonth2() {
        return month2;
    }
}
