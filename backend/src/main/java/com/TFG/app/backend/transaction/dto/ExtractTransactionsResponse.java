package com.TFG.app.backend.transaction.dto;

import java.util.List;

public class ExtractTransactionsResponse {

    private List<Object[]> transactions;

    public ExtractTransactionsResponse() {
    }

    public List<Object[]> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Object[]> transactions) {
        this.transactions = transactions;
    }
}
