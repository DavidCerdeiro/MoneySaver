package com.TFG.app.backend.goal.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.goal.entity.Goal;
import com.TFG.app.backend.goal.repository.GoalRepository;

@Service
public class GoalService {
    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }
}
