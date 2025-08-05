package com.TFG.app.backend.periodic_spending.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;
import java.util.List;
import java.util.Date;

public interface Periodic_SpendingRepository extends JpaRepository<Periodic_Spending, Integer> {
  
    public Periodic_Spending findBySpendingId(Integer spendingId);

    public List<Periodic_Spending> findByExpirationAfter(Date date);
}
