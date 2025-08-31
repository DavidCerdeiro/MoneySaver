package com.TFG.app.backend.transaction.dto;

import java.math.BigDecimal;

public class AddTransactionResponse {
    private String name;
    private BigDecimal amount;

    public AddTransactionResponse(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
