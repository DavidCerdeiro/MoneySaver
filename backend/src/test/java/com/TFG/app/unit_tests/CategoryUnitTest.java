package com.TFG.app.unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.user.entity.User;
import java.math.BigDecimal;

public class CategoryUnitTest {
    @Test
    public void testCategoryAttributes() {
        Category category = new Category();
        User user = new User();

        user.setName("Paco");
        category.setName("Food");
        category.setIcon("heavy_dollar_sign");
        category.setUser(user);

        Assertions.assertEquals("Food", category.getName());
        Assertions.assertEquals("heavy_dollar_sign", category.getIcon());
        Assertions.assertEquals(user, category.getUser());
    }

    @Test
    public void testCategoryDefaultValues() {
        Category category = new Category();

        Assertions.assertEquals(BigDecimal.ZERO, category.getTotalSpending());
    }

    @Test
    public void testTotalSpendingScaleIsTwo() {
        Category category = new Category();
        BigDecimal inputTotalSpending = new BigDecimal("123.4567");

        category.setTotalSpending(inputTotalSpending);

        Assertions.assertEquals(2, category.getTotalSpending().scale(), "Total spending should have 2 decimal places");
        Assertions.assertEquals(new BigDecimal("123.46"), category.getTotalSpending());
    }
}
