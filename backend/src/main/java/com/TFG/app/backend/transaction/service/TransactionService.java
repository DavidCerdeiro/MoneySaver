package com.TFG.app.backend.transaction.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.account.service.AccountService;
import com.TFG.app.backend.transaction.entity.Transaction;
import com.TFG.app.backend.transaction.repository.TransactionRepository;
import com.TFG.app.backend.user.entity.User;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
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

    public List<Transaction> getAllByUserAndMonth(User idUser, int month, int year) {
        List<Account> accounts = accountService.findByUser(idUser);
        List<Transaction> transactions = new ArrayList<>();
        for (Account account : accounts) {
            transactions.addAll(transactionRepository.getAllByAccountAndMonth(account.getId(), month, year));
        }
        return transactions;
    }
}
