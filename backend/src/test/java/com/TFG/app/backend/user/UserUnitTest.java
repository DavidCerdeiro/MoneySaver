package com.TFG.app.backend.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.user.entity.User;

public class UserUnitTest {
    @Test
    public void testUserAttributes() {
        User user = new User();
        Type_Chart typeChart = new Type_Chart();

        typeChart.setName("bars");
        user.setName("Lionel");
        user.setSurname("Messi");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setIsAuthenticated(true);
        user.setTypeChart(typeChart);

        Assertions.assertEquals("Lionel", user.getName());
        Assertions.assertEquals("Messi", user.getSurname());
        Assertions.assertEquals("test@example.com", user.getEmail());
        Assertions.assertEquals("password123", user.getPassword());
        Assertions.assertTrue(user.getIsAuthenticated());
        Assertions.assertEquals(typeChart, user.getTypeChart());
    }

    @Test
    public void testDefaultIsAuthenticated() {
        User user = new User();
        Assertions.assertFalse(user.getIsAuthenticated(), "User should not be authenticated by default");
    }   
}
