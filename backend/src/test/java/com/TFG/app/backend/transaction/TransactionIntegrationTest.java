package com.TFG.app.backend.transaction;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.account.repository.AccountRepository;
import com.TFG.app.backend.category.CategoryTestDataBuilder;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.category.repository.CategoryRepository;
import com.TFG.app.backend.spending.SpendingTestDataBuilder;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.spending.repository.SpendingRepository;
import com.TFG.app.backend.transaction.entity.Transaction;
import com.TFG.app.backend.transaction.repository.TransactionRepository;
import com.TFG.app.backend.type_chart.TypeChartTestDataBuilder;
import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.repository.Type_ChartRepository;
import com.TFG.app.backend.user.UserTestDataBuilder;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;

@DataJpaTest
@ActiveProfiles("test")
public class TransactionIntegrationTest {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpendingRepository spendingRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Type_ChartRepository typeChartRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateTransaction() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().withTypeChart(typeChart).build();
        userRepository.save(user);

        Category category = new CategoryTestDataBuilder().withUser(user).build();
        categoryRepository.save(category);

        Spending spending = new SpendingTestDataBuilder().withCategory(category).build();
        spendingRepository.save(spending);

        Account account = new Account();
        Transaction transaction = new Transaction();

        account.setUser(user);
        account.setName("Cuenta de Ahorros");
        account.setNumber("1234567890abcdef1234567890abcdef12345678");
        account.setBankName("Unicaja");
        account.setAccountCode("tr_id123");
        accountRepository.save(account);

        transaction.setAccount(account);
        transaction.setTransactionCode("tr_id12345");
        transaction.setSpending(spending);
        transactionRepository.save(transaction);

        Assertions.assertTrue(transactionRepository.findBySpending(spending).isPresent());
    }

    @Test
    public void testSaveDuplicateTrueLayerThrowsException() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().withTypeChart(typeChart).build();
        userRepository.save(user);

        Category category = new CategoryTestDataBuilder().withUser(user).build();
        categoryRepository.save(category);

        Spending spending = new SpendingTestDataBuilder().withCategory(category).build();
        spendingRepository.save(spending);

        Account account = new Account();
        Transaction transaction = new Transaction();
        Transaction transaction2 = new Transaction();

        account.setUser(user);
        account.setName("Cuenta de Ahorros");
        account.setNumber("1234567890abcdef1234567890abcdef12345678");
        account.setBankName("Unicaja");
        account.setAccountCode("tr_id1234577");
        accountRepository.save(account);

        
        transaction.setAccount(account);
        transaction.setTransactionCode("tr_id12345");
        transaction.setSpending(spending);
        transactionRepository.save(transaction);

        
        transaction2.setAccount(account);
        transaction2.setTransactionCode("tr_id12345");
        transaction2.setSpending(spending);

        assertThrows(DataIntegrityViolationException.class, () -> { transactionRepository.saveAndFlush(transaction2); });
    }
}