package com.TFG.app.backend.bank_transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.bank_transaction.entity.Bank_Transaction;

public interface Bank_TransactionRepository extends JpaRepository<Bank_Transaction, Long> {
    
}
