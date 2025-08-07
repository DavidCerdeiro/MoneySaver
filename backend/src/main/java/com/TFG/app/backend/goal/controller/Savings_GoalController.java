package com.TFG.app.backend.goal.controller;

import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.goal.entity.Goal;
import com.TFG.app.backend.goal.service.GoalService;

@RestController
@RequestMapping("/api/savings-goals")
public class Savings_GoalController {
    private final GoalService savingsGoalService;

    public Savings_GoalController(GoalService savingsGoalService) {
        this.savingsGoalService = savingsGoalService;
    }

    @PostMapping("/create")
    public Goal create(Goal goal) {
        return savingsGoalService.createGoal(goal);
    }
}
