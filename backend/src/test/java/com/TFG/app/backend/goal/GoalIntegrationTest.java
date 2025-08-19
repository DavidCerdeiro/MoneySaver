package com.TFG.app.backend.goal;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.TFG.app.backend.category.CategoryTestDataBuilder;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.category.repository.CategoryRepository;
import com.TFG.app.backend.goal.entity.Goal;
import com.TFG.app.backend.goal.repository.GoalRepository;
import com.TFG.app.backend.type_chart.TypeChartTestDataBuilder;
import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.repository.Type_ChartRepository;
import com.TFG.app.backend.user.UserTestDataBuilder;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.repository.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
public class GoalIntegrationTest {
    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Type_ChartRepository typeChartRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateGoal() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().withTypeChart(typeChart).build();
        userRepository.save(user);

        Category category = new CategoryTestDataBuilder().withUser(user).build();
        categoryRepository.save(category);


        Goal goal = new Goal();
        goal.setName("Holidays in Brasil");
        goal.setCategory(category);
        goal.setTargetAmount(BigDecimal.valueOf(1000.0));

        goalRepository.save(goal);
    }
}
