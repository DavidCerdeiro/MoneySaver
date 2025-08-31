package com.TFG.app.backend.transaction.dto;

import com.TFG.app.backend.account.dto.AccountDTO;

public class TransactionDTO {
    private AccountDTO account;
    private String trueLayerId;

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public String getTrueLayerId() {
        return trueLayerId;
    }

    public void setTrueLayerId(String trueLayerId) {
        this.trueLayerId = trueLayerId;
    }
}
