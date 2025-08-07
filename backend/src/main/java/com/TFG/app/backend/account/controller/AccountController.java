package com.TFG.app.backend.account.controller;

import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.account.service.AccountService;

@RestController
@RequestMapping("/api/bank_accounts")
public class AccountController {
    private final AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public Account create(@RequestBody Account account) {
        return accountService.createAccount(account);
    }
}
