package com.TFG.app.backend.periodic_spending;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.TFG.app.backend.category.CategoryTestDataBuilder;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.category.repository.CategoryRepository;
import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;
import com.TFG.app.backend.periodic_spending.repository.Periodic_SpendingRepository;
import com.TFG.app.backend.spending.SpendingTestDataBuilder;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.spending.repository.SpendingRepository;
import com.TFG.app.backend.type_chart.TypeChartTestDataBuilder;
import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.repository.Type_ChartRepository;
import com.TFG.app.backend.type_periodic.entity.Type_Periodic;
import com.TFG.app.backend.type_periodic.repository.Type_PeriodicRepository;
import com.TFG.app.backend.user.UserTestDataBuilder;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.repository.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
public class Periodic_SpendingIntegrationTest {
    @Autowired
    private Periodic_SpendingRepository periodicSpendingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpendingRepository spendingRepository;

    @Autowired
    private Type_ChartRepository typeChartRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Type_PeriodicRepository typePeriodicRepository;

    @Test
    public void testCreatePeriodicSpending() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().withTypeChart(typeChart).build();
        userRepository.save(user);

        Category category = new CategoryTestDataBuilder().withUser(user).build();
        categoryRepository.save(category);

        Spending spending = new SpendingTestDataBuilder().withCategory(category).withIsPeriodic(true).build();
        spendingRepository.save(spending);

        Type_Periodic typePeriodic = new Type_Periodic();
        typePeriodic.setName("Monthly");
        typePeriodicRepository.save(typePeriodic);

        Periodic_Spending periodicSpending = new Periodic_Spending();

        periodicSpending.setSpending(spending);
        periodicSpending.setTypePeriodic(typePeriodic);
        periodicSpending.setExpiration(LocalDate.now().plusMonths(1));
        periodicSpending.setLastPayment(LocalDate.now());
        periodicSpendingRepository.save(periodicSpending);

        Assertions.assertEquals(typePeriodic.getName(), periodicSpendingRepository.findBySpendingId(spending.getId()).getTypePeriodic().getName());
        Assertions.assertEquals(LocalDate.now().plusMonths(1).getMonthValue(), periodicSpendingRepository.findBySpendingId(spending.getId()).getExpiration().getMonthValue());
        Assertions.assertEquals(LocalDate.now().getYear(), periodicSpendingRepository.findBySpendingId(spending.getId()).getExpiration().getYear());
        Assertions.assertEquals(LocalDate.now().getDayOfMonth(), periodicSpendingRepository.findBySpendingId(spending.getId()).getLastPayment().getDayOfMonth());
    }
}
