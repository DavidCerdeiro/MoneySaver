package com.TFG.app.backend.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.user.entity.User;
public class AccountUnitTest {
    @Test
    public void testAccountAttributes() {
        Account account = new Account();
        User user = new User();

        user.setName("Juan Perez");
        account.setBankName("Bank of Spain");
        account.setAccessToken("1234567890abcdef1234567890abcdef12345678");
        account.setUser(user);

        Assertions.assertEquals("Bank of Spain", account.getBankName());
        Assertions.assertEquals("1234567890abcdef1234567890abcdef12345678", account.getAccessToken());
        Assertions.assertEquals(user, account.getUser());
    }
}
