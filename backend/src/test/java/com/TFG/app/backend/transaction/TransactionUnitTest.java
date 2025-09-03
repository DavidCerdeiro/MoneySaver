package com.TFG.app.backend.transaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.transaction.entity.Transaction;

public class TransactionUnitTest {
    @Test
    public void testTransactionAttributes() {
        Transaction transaction = new Transaction();
        Account bankAccount = new Account();
        Spending spending = new Spending();

        spending.setName("Mercadona");
        bankAccount.setBankName("Unicaja");
        transaction.setAccount(bankAccount);
        transaction.setSpending(spending);
        transaction.setTransactionCode("tr_id12345");

        Assertions.assertEquals("Mercadona", transaction.getSpending().getName());
        Assertions.assertEquals("Unicaja", transaction.getAccount().getBankName());
        Assertions.assertEquals(spending, transaction.getSpending());
        Assertions.assertEquals("tr_id12345", transaction.getTransactionCode());
    }
}
