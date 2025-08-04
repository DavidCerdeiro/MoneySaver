package com.TFG.app.backend.spending.dto;

import java.util.List;
public class AllSpendingFromUserMonthAndYearResponse {
    private List<SpendingResponse> spendings;

    public AllSpendingFromUserMonthAndYearResponse(List<SpendingResponse> spendings) {
        this.spendings = spendings;
    }

    public List<SpendingResponse> getSpendings() {
        return spendings;
    }
}
