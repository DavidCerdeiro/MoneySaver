package com.TFG.app.backend.bank_transaction.controller;

import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.bank_transaction.service.Bank_TransactionService;
import com.TFG.app.backend.bank_transaction.entity.Bank_Transaction;

@RestController
@RequestMapping("/api/bank-transactions")
public class Bank_TransactionController {
    private final Bank_TransactionService bankTransactionService;

    public Bank_TransactionController(Bank_TransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }
    
    @PostMapping("/create")
    public Bank_Transaction create(@RequestBody Bank_Transaction bankTransaction) {
        return bankTransactionService.createBankTransaction(bankTransaction);
    }
}
