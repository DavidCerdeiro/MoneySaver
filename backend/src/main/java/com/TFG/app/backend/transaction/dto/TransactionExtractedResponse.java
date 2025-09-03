package com.TFG.app.backend.transaction.dto;

import com.TFG.app.backend.account.entity.Account;

public class TransactionExtractedResponse {
    private String transactionCode;
    private Account account;

    public TransactionExtractedResponse(String transactionCode, Account account) {
        this.transactionCode = transactionCode;
        this.account = account;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public Account getAccount() {
        return account;
    }
}
