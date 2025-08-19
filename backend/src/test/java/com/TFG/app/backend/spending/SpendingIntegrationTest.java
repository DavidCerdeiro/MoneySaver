package com.TFG.app.backend.spending;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.TFG.app.backend.category.CategoryTestDataBuilder;
import com.TFG.app.backend.category.repository.CategoryRepository;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.spending.repository.SpendingRepository;
import com.TFG.app.backend.type_chart.TypeChartTestDataBuilder;
import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.repository.Type_ChartRepository;
import com.TFG.app.backend.user.UserTestDataBuilder;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.repository.UserRepository;
import com.TFG.app.backend.category.entity.Category;

@DataJpaTest
@ActiveProfiles("test")
public class SpendingIntegrationTest {
    @Autowired
    private SpendingRepository spendingRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Type_ChartRepository typeChartRepository;

    @Test
    public void testCreateSpending() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().
        withTypeChart(typeChart).
        build();
        userRepository.save(user);

        Category category = new CategoryTestDataBuilder().
        withUser(user).
        build();
        categoryRepository.save(category);

        Spending spending = new SpendingTestDataBuilder().withCategory(category).build();
        spendingRepository.save(spending);

        List<Spending> spendings = spendingRepository.findByCategory(category);
        
        assertThat(spendings).isNotEmpty();
    }

}

