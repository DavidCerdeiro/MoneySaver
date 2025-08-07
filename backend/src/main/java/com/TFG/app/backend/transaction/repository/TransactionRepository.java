package com.TFG.app.backend.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
