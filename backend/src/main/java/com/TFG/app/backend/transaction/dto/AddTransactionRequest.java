package com.TFG.app.backend.transaction.dto;

import com.TFG.app.backend.spending.dto.AddSpendingRequest;

public class AddTransactionRequest {
    private TransactionDTO transaction;
    private AddSpendingRequest spending;

    public TransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDTO transaction) {
        this.transaction = transaction;
    }


    public AddSpendingRequest getSpending() {
        return spending;
    }

    public void setSpending(AddSpendingRequest spending) {
        this.spending = spending;
    }
}
