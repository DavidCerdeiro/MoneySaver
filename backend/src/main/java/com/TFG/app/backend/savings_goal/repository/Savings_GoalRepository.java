package com.TFG.app.backend.savings_goal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.savings_goal.entity.Savings_Goal;

public interface Savings_GoalRepository extends JpaRepository<Savings_Goal, Integer> {
    
}
