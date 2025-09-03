package com.TFG.app.backend.transaction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.transaction.entity.Transaction;
import com.TFG.app.backend.transaction.repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public boolean existsByTransactionCode(String transactionCode) {
        return transactionRepository.existsByTransactionCode(transactionCode);
    }
    public List<Transaction> getAllByAccountAndMonth(Integer idAccount, int month, int year) {
        return transactionRepository.getAllByAccountAndMonth(idAccount, month, year);
    }
}
