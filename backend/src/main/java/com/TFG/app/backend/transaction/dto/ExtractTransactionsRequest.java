package com.TFG.app.backend.transaction.dto;

import com.TFG.app.backend.account.entity.Account;

public class ExtractTransactionsRequest {
    private Account account;
    private String minDate;
    private String maxDate;
    private String code;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

}
