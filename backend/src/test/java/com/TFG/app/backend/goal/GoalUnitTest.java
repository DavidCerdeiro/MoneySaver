package com.TFG.app.backend.goal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.goal.entity.Goal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

public class GoalUnitTest {
    @Test
    public void testGoalAttributes() {
        Goal goal = new Goal();
        Category category = new Category();

        category.setName("Travel");
        goal.setName("Trip to Venecia");
        goal.setTargetAmount(new BigDecimal("250.00"));
        goal.setCategory(category);

        Assertions.assertEquals("Trip to Venecia", goal.getName());
        Assertions.assertEquals(new BigDecimal("250.00"), goal.getTargetAmount());
        Assertions.assertEquals(category, goal.getCategory());
    }
    
    @Test
    public void testTargetAmountScaleIsTwo() {
        Goal goal = new Goal();
        BigDecimal inputTargetAmount = new BigDecimal("123.4567");

        goal.setTargetAmount(inputTargetAmount);

        assertEquals(2, goal.getTargetAmount().scale());
        assertEquals(new BigDecimal("123.46"), goal.getTargetAmount());
    }
}
