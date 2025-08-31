package com.TFG.app.backend.goal.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.goal.entity.Goal;
import com.TFG.app.backend.goal.repository.GoalRepository;

@Service
public class GoalService {
    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public Goal addGoal(String name, BigDecimal targetAmount, Category category) {
        Goal goal = new Goal();
        goal.setName(name);
        goal.setTargetAmount(targetAmount);
        goal.setCategory(category);

        return goalRepository.save(goal);
    }

    public List<Goal> getAllGoalsFromCategories(Integer idUser, LocalDate month) {
        return goalRepository.findActivesByUserAndMonth(idUser, month);
    }

    public List<Goal> getAllGoalsFromCategory(Category category) {
        return goalRepository.findByCategoryAndDeletedAtIsNull(category);
    }

    public BigDecimal calculatePercent(BigDecimal targetAmount, BigDecimal currentAmount) {
        if (targetAmount == null || currentAmount == null || targetAmount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return currentAmount
                .divide(targetAmount, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))             
                .setScale(2, RoundingMode.HALF_UP); 
    }

    public boolean deleteGoal(Integer idGoal) {
        if (goalRepository.existsById(idGoal)) {
            goalRepository.deleteById(idGoal);
            return true;
        }
        return false;
    }

    public Goal findById(Integer id) {
        return goalRepository.findById(id).orElse(null);
    }

    public Goal updateGoal(Goal goal) {
        return goalRepository.save(goal);
    }
}
