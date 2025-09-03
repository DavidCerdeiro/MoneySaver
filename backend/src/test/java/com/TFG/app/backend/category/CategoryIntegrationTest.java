package com.TFG.app.backend.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.TFG.app.backend.user.UserTestDataBuilder;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.category.repository.CategoryRepository;
import com.TFG.app.backend.type_chart.TypeChartTestDataBuilder;
import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.repository.Type_ChartRepository;
import com.TFG.app.backend.user.repository.UserRepository;

import java.time.LocalDate;


@DataJpaTest
@ActiveProfiles("test")
public class CategoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Type_ChartRepository typeChartRepository;

    @Test
    public void testSaveCategory() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().withTypeChart(typeChart).build();
        userRepository.save(user);

        Category category = new Category();

        category.setIcon("heavy_dollar_sign");
        category.setName("Videogames");
        category.setUser(user);
        categoryRepository.save(category);

        Assertions.assertTrue(categoryRepository.existsByNameAndUserAndDeletedAtIsNull("Videogames", user));
    }

    @Test
    public void testUpdateCategory() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().withTypeChart(typeChart).build();
        userRepository.save(user);

        Category category = new Category();

        category.setIcon("heavy_dollar_sign");
        category.setName("Videogames");
        category.setUser(user);
        categoryRepository.save(category);

        category.setName("Supermarkets");
        categoryRepository.save(category);

        Assertions.assertTrue(categoryRepository.existsByNameAndUserAndDeletedAtIsNull("Supermarkets", user));
    }

    @Test
    public void testCreatedAtPrePersist() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().withTypeChart(typeChart).build();
        userRepository.save(user);

        Category category = new Category();

        category.setIcon("heavy_dollar_sign");
        category.setName("Videogames");
        category.setUser(user);
        categoryRepository.save(category);

        Assertions.assertEquals(1, category.getCreatedAt().getDayOfMonth());
        Assertions.assertEquals(LocalDate.now().getMonthValue(), category.getCreatedAt().getMonthValue());
        Assertions.assertEquals(LocalDate.now().getYear(), category.getCreatedAt().getYear());
    }

}
