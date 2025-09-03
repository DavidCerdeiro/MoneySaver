package com.TFG.app.backend.account.dto;

public class AccountResponse {
    private Integer id;
    private String accountCode;
    private String name;
    private String number;
    private String bankName;

    public AccountResponse(Integer id, String accountCode, String name, String number, String bankName) {
        this.id = id;
        this.accountCode = accountCode;
        this.name = name;
        this.number = number;
        this.bankName = bankName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getAccountCode() {
        return accountCode;
    }
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }
}
