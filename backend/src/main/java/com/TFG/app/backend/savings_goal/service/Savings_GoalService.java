package com.TFG.app.backend.savings_goal.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.savings_goal.repository.Savings_GoalRepository;
import com.TFG.app.backend.savings_goal.entity.Savings_Goal;

@Service
public class Savings_GoalService {
    private final Savings_GoalRepository savingsGoalRepository;

    public Savings_GoalService(Savings_GoalRepository savingsGoalRepository) {
        this.savingsGoalRepository = savingsGoalRepository;
    }   

    public Savings_Goal createSavingsGoal(Savings_Goal savingsGoal) {
        return savingsGoalRepository.save(savingsGoal);
    }    
}
