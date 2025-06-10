package com.TFG.app.backend.periodic_spending.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;

public interface Periodic_SpendingRepository extends JpaRepository<Periodic_Spending, Integer> {
  
}
