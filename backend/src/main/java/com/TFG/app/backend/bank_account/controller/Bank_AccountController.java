package com.TFG.app.backend.bank_account.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TFG.app.backend.bank_account.entity.Bank_Account;
import com.TFG.app.backend.bank_account.service.Bank_AccountService;

@RestController
@RequestMapping("/api/bank_accounts")
public class Bank_AccountController {
    private final Bank_AccountService bankAccountService;
    public Bank_AccountController(Bank_AccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public Bank_Account create(@RequestBody Bank_Account bankAccount) {
        return bankAccountService.createBankAccount(bankAccount);
    }
}
