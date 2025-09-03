package com.TFG.app.backend.transaction.dto;

import com.TFG.app.backend.account.dto.AccountDTO;

public class TransactionDTO {
    private AccountDTO account;
    private String transactionCode;

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }
}
