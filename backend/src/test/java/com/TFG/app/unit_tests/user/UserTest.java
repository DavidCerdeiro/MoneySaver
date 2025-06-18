package com.TFG.app.unit_tests.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import com.TFG.app.backend.user.entity.User;

public class UserTest {
    @Test
    public void testUserAttributes() {
        User user = new User();

        user.setName("Lionel");
        user.setSurname("Messi");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setIsAuthenticated(true);

        Assertions.assertEquals("Lionel", user.getName());
        Assertions.assertEquals("Messi", user.getSurname());
        Assertions.assertEquals("test@example.com", user.getEmail());
        Assertions.assertEquals("password123", user.getPassword());
        Assertions.assertTrue(user.getIsAuthenticated());
    }

    @Test
    public void testDefaultIsAuthenticated() {
        User user = new User();
        Assertions.assertFalse(user.getIsAuthenticated(), "User should not be authenticated by default");
    }   
}
