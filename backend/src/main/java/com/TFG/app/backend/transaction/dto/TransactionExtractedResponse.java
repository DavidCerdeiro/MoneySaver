package com.TFG.app.backend.transaction.dto;

import com.TFG.app.backend.account.entity.Account;

public class TransactionExtractedResponse {
    private String trueLayerId;
    private Account account;

    public TransactionExtractedResponse(String trueLayerId, Account account) {
        this.trueLayerId = trueLayerId;
        this.account = account;
    }

    public String getTrueLayerId() {
        return trueLayerId;
    }

    public Account getAccount() {
        return account;
    }
}
