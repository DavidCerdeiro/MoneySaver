package com.TFG.app.unit_tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.TFG.app.backend.bank_transaction.entity.Bank_Transaction;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.bank_account.entity.Bank_Account;

public class Bank_TransactionUnitTest {
    @Test
    public void testBankTransactionAttributes() {
        Bank_Transaction bankTransaction = new Bank_Transaction();
        Bank_Account bankAccount = new Bank_Account();
        Spending spending = new Spending();

        spending.setName("Grocery Shopping");
        bankAccount.setBankName("Bank of Spain");
        bankTransaction.setBankAccount(bankAccount);
        bankTransaction.setSpending(spending);

        Assertions.assertEquals("Grocery Shopping", bankTransaction.getSpending().getName());
        Assertions.assertEquals("Bank of Spain", bankTransaction.getBankAccount().getBankName());
        Assertions.assertEquals(spending, bankTransaction.getSpending());
    }
}
