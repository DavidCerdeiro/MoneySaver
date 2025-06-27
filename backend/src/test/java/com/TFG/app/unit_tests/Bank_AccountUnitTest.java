package com.TFG.app.unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import com.TFG.app.backend.bank_account.entity.Bank_Account;
import com.TFG.app.backend.user.entity.User;
public class Bank_AccountUnitTest {
    @Test
    public void testBankAccountAttributes() {
        Bank_Account bankAccount = new Bank_Account();
        User user = new User();

        user.setName("Juan Perez");
        bankAccount.setBankName("Bank of Spain");
        bankAccount.setAccessToken("1234567890abcdef1234567890abcdef12345678");
        bankAccount.setUser(user);

        Assertions.assertEquals("Bank of Spain", bankAccount.getBankName());
        Assertions.assertEquals("1234567890abcdef1234567890abcdef12345678", bankAccount.getAccessToken());
        Assertions.assertEquals(user, bankAccount.getUser());
    }
}
