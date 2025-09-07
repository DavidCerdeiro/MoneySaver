package com.TFG.app.backend.spending;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
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

        Spending spending = new Spending();

        spending.setName("Amazon");
        spending.setCategory(category);
        spending.setAmount(BigDecimal.valueOf(100.0));
        spending.setDate(LocalDate.now());
        spendingRepository.save(spending);


        Assertions.assertEquals(1, spendingRepository.findByCategory(category).size());
        Assertions.assertEquals("Amazon", spendingRepository.findByCategory(category).get(0).getName());
        Assertions.assertEquals(BigDecimal.valueOf(100.00).setScale(2), spendingRepository.findByCategory(category).get(0).getAmount());
        Assertions.assertEquals(LocalDate.now(), spendingRepository.findByCategory(category).get(0).getDate());
    }

}

