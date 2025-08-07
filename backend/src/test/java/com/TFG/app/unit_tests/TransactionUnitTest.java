package com.TFG.app.unit_tests;

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

        spending.setName("Grocery Shopping");
        bankAccount.setBankName("Bank of Spain");
        transaction.setAccount(bankAccount);
        transaction.setSpending(spending);

        Assertions.assertEquals("Grocery Shopping", transaction.getSpending().getName());
        Assertions.assertEquals("Bank of Spain", transaction.getAccount().getBankName());
        Assertions.assertEquals(spending, transaction.getSpending());
    }
}
