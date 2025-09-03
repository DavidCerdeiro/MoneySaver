package com.TFG.app.backend.account.dto;

import java.util.List;

import com.TFG.app.backend.account.entity.Account;

public class GetAccountsResponse {
    private List<AccountResponse> accounts;

    public GetAccountsResponse(List<Account> accounts) {
        this.accounts = accounts.stream()
                .map(account -> new AccountResponse(account.getId(), account.getAccountCode(), account.getName(), account.getNumber(), account.getBankName()))
                .toList();
    }

    public List<AccountResponse> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountResponse> accounts) {
        this.accounts = accounts;
    }
}
