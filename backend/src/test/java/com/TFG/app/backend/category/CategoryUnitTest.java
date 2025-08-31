package com.TFG.app.backend.category;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.user.entity.User;

public class CategoryUnitTest {
    @Test
    public void testCategoryAttributes() {
        Category category = new Category();
        User user = new User();

        user.setName("Paco");
        category.setName("Food");
        category.setIcon("heavy_dollar_sign");
        category.setCreatedAt(LocalDate.now());
        category.setUser(user);

        Assertions.assertEquals("Food", category.getName());
        Assertions.assertEquals("heavy_dollar_sign", category.getIcon());
        Assertions.assertEquals(user, category.getUser());
        Assertions.assertEquals(LocalDate.now(), category.getCreatedAt());
    }

}
