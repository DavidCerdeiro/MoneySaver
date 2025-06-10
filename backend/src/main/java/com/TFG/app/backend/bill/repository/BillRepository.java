package com.TFG.app.backend.bill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TFG.app.backend.bill.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer> {
} 