package com.TFG.app.backend.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.transaction.entity.Transaction;
import java.util.Optional;

import com.TFG.app.backend.spending.entity.Spending;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Optional<Transaction> findBySpending(Spending spending);
}
