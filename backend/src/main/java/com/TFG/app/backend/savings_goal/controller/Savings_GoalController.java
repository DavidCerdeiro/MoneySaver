package com.TFG.app.backend.savings_goal.controller;

import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.savings_goal.entity.Savings_Goal;
import com.TFG.app.backend.savings_goal.service.Savings_GoalService;

@RestController
@RequestMapping("/api/savings-goals")
public class Savings_GoalController {
    private final Savings_GoalService savingsGoalService;

    public Savings_GoalController(Savings_GoalService savingsGoalService) {
        this.savingsGoalService = savingsGoalService;
    }

    @PostMapping("/create")
    public Savings_Goal create(Savings_Goal savingsGoal) {
        return savingsGoalService.createSavingsGoal(savingsGoal);
    }
}
