package com.TFG.app.backend.bank_transaction.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.bank_transaction.repository.Bank_TransactionRepository;
import com.TFG.app.backend.bank_transaction.entity.Bank_Transaction;

@Service
public class Bank_TransactionService {
    private final Bank_TransactionRepository bankTransactionRepository;
    public Bank_TransactionService(Bank_TransactionRepository bankTransactionRepository) {
        this.bankTransactionRepository = bankTransactionRepository;
    }
    
    public Bank_Transaction createBankTransaction(Bank_Transaction bankTransaction) {
        return bankTransactionRepository.save(bankTransaction);
    }
}
