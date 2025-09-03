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
        account.setBankName("CaixaBank");
        account.setName("Cuenta de ahorros");
        account.setNumber("100000");
        account.setAccountCode("tl_id1234");
        account.setUser(user);

        Assertions.assertEquals("CaixaBank", account.getBankName());
        Assertions.assertEquals("100000", account.getNumber());
        Assertions.assertEquals("tl_id1234", account.getAccountCode());
        Assertions.assertEquals("Juan Perez", account.getUser().getName());
    }
}
