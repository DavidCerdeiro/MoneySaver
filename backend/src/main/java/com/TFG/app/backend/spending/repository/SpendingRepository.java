package com.TFG.app.backend.spending.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.spending.entity.Spending;

public interface SpendingRepository extends JpaRepository<Spending, Integer> {
    
}
