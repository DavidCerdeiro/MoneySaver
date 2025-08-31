package com.TFG.app.backend.account.dto;

public class AccountDTO {
    private Integer id;
    private String name;
    private String bankName;
    private String number;

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
}
