package com.TFG.app.backend.transaction.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.transaction.entity.Transaction;
import com.TFG.app.backend.transaction.repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createBankTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
