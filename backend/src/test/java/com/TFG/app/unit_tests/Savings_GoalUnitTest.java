package com.TFG.app.unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import com.TFG.app.backend.savings_goal.entity.Savings_Goal;
import com.TFG.app.backend.category.entity.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

public class Savings_GoalUnitTest {
    @Test
    public void testSavingsGoalAttributes() {
        Savings_Goal savingsGoal = new Savings_Goal();
        Category category = new Category();

        category.setName("Travel");
        savingsGoal.setName("Trip to Venecia");
        savingsGoal.setTargetAmount(new BigDecimal("250.00"));
        savingsGoal.setCategory(category);

        Assertions.assertEquals("Trip to Venecia", savingsGoal.getName());
        Assertions.assertEquals(new BigDecimal("250.00"), savingsGoal.getTargetAmount());
        Assertions.assertEquals(category, savingsGoal.getCategory());
    }
    
    @Test
    public void testTargetAmountScaleIsTwo() {
        Savings_Goal savingsGoal = new Savings_Goal();
        BigDecimal inputTargetAmount = new BigDecimal("123.4567");

        savingsGoal.setTargetAmount(inputTargetAmount);

        assertEquals(2, savingsGoal.getTargetAmount().scale(), "Amount should have 2 decimal places");
        assertEquals(new BigDecimal("123.46"), savingsGoal.getTargetAmount());
    }
}
