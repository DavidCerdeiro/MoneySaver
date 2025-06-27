package com.TFG.app.unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.category.entity.Category;

import java.math.BigDecimal;
import java.util.Date;

public class SpendingUnitTest {
    @Test
    public void testSpendingAttributes() {
        Spending spending = new Spending();
        Category category = new Category();
        Establishment establishment = new Establishment();
        User user = new User();

        user.setName("Manolo");
        category.setName("Video Games");
        establishment.setName("GameStop");
        spending.setName("GTA VI");
        spending.setAmount(new BigDecimal("100.00"));
        spending.setDate(new Date());
        spending.setIsPeriodic(false);
        spending.setCategory(category);
        spending.setEstablishment(establishment);
        spending.setUser(user);

        Assertions.assertEquals("GTA VI", spending.getName());
        Assertions.assertEquals(new BigDecimal("100.00"), spending.getAmount());
        Assertions.assertNotNull(spending.getDate());
        Assertions.assertFalse(spending.getIsPeriodic());
        Assertions.assertEquals(category, spending.getCategory());
        Assertions.assertEquals(establishment, spending.getEstablishment());
        Assertions.assertEquals(user, spending.getUser());
    }

    @Test
    public void testAmountScaleIsTwo() {
        Spending spending = new Spending();
        BigDecimal inputAmount = new BigDecimal("123.4567");

        spending.setAmount(inputAmount);

        assertEquals(2, spending.getAmount().scale(), "Amount should have 2 decimal places");
        assertEquals(new BigDecimal("123.46"), spending.getAmount());
    }
}
