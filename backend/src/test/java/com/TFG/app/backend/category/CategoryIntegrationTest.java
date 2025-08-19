package com.TFG.app.backend.category;

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

import static org.assertj.core.api.Assertions.assertThat;

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

        Category category = new CategoryTestDataBuilder().withUser(user).build();

        categoryRepository.save(category);

        assertThat(categoryRepository.existsByNameAndUserAndIsDeletedFalse("Videogames", user)).isTrue();
    }

    @Test
    public void testUpdateCategory() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().withTypeChart(typeChart).build();
        userRepository.save(user);

        Category category = new CategoryTestDataBuilder().withUser(user).build();
        categoryRepository.save(category);

        category.setName("Supermarkets");
        categoryRepository.save(category);

        assertThat(categoryRepository.existsByNameAndUserAndIsDeletedFalse("Supermarkets", user)).isTrue();
    }

    @Test
    public void testDuplicateNameDifferentUser() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user1 = new UserTestDataBuilder().withTypeChart(typeChart).build();
        userRepository.save(user1);

        User user2 = new UserTestDataBuilder().withEmail("david2@gmail.com").withTypeChart(typeChart).build();
        userRepository.save(user2);

        Category category1 = new CategoryTestDataBuilder().withUser(user1).build();
        categoryRepository.save(category1);

        Category category2 = new CategoryTestDataBuilder().withUser(user2).build();
        categoryRepository.save(category2);

        assertThat(categoryRepository.existsByNameAndUserAndIsDeletedFalse("Videogames", user1)).isTrue();
        assertThat(categoryRepository.existsByNameAndUserAndIsDeletedFalse("Videogames", user2)).isTrue();
    }

    @Test
    public void testInsertCategoryWithSameNameAfterDelete() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().withTypeChart(typeChart).build();
        userRepository.save(user);

        Category category1 = new CategoryTestDataBuilder().withUser(user).build();
        categoryRepository.save(category1);

        category1.setDeleted(true);
        categoryRepository.save(category1);

        Category category2 = new CategoryTestDataBuilder().withUser(user).build();
        categoryRepository.save(category2);

        assertThat(categoryRepository.existsByNameAndUserAndIsDeletedFalse("Videogames", user)).isTrue();
    }
}
