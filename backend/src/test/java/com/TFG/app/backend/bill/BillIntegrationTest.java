package com.TFG.app.backend.bill;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import com.TFG.app.backend.bill.entity.Bill;
import com.TFG.app.backend.bill.repository.BillRepository;
import com.TFG.app.backend.category.CategoryTestDataBuilder;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.category.repository.CategoryRepository;
import com.TFG.app.backend.spending.SpendingTestDataBuilder;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.spending.repository.SpendingRepository;
import com.TFG.app.backend.type_chart.TypeChartTestDataBuilder;
import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.repository.Type_ChartRepository;
import com.TFG.app.backend.user.UserTestDataBuilder;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.repository.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
public class BillIntegrationTest {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private SpendingRepository spendingRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Type_ChartRepository typeChartRepository;

    @Test
    public void testCreateBill() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().withTypeChart(typeChart).build();
        userRepository.save(user);

        Category category = new CategoryTestDataBuilder().withUser(user).build();
        categoryRepository.save(category);

        Spending spending = new SpendingTestDataBuilder().withCategory(category).build();
        spendingRepository.save(spending);

        Bill bill = new Bill();
        bill.setSpending(spending);
        bill.setFileRoute("<file_route>");
        billRepository.save(bill);

        assertThat(billRepository.findById(bill.getId())).isPresent();
    }
}