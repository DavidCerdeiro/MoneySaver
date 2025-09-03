package com.TFG.app.backend.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TFG.app.backend.transaction.entity.Transaction;
import com.TFG.app.backend.spending.entity.Spending;

import java.util.List;
import java.util.Optional;




public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Optional<Transaction> findBySpending(Spending spending);

    boolean existsByTransactionCode(String transactionCode);

    @Query(value = """
        SELECT t.* FROM transaction t
        JOIN spending s ON t."Id_Spending" = s."Id"
        WHERE t."Id_Account" = :accountId
        AND EXTRACT(YEAR FROM s."Date") = :year
        AND EXTRACT(MONTH FROM s."Date") = :month
    """, nativeQuery = true)
    List<Transaction> getAllByAccountAndMonth(
        @Param("accountId") int accountId,
        @Param("month") int month,
        @Param("year") int year
    );

}
