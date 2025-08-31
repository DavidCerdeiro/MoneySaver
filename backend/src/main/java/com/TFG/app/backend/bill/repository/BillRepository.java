package com.TFG.app.backend.bill.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TFG.app.backend.bill.entity.Bill;
import com.TFG.app.backend.spending.entity.Spending;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    Optional<Bill> findBySpending(Spending spending);
} 