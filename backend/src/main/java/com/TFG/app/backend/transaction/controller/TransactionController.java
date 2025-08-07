package com.TFG.app.backend.transaction.controller;

import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.transaction.entity.Transaction;
import com.TFG.app.backend.transaction.service.TransactionService;

@RestController
@RequestMapping("/api/bank-transactions")
public class TransactionController {
    private final TransactionService bankTransactionService;

    public TransactionController(TransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }
    
    @PostMapping("/create")
    public Transaction create(@RequestBody Transaction transaction) {
        return bankTransactionService.createBankTransaction(transaction);
    }
}
