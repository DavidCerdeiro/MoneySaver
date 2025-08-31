package com.TFG.app.backend.account;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.account.repository.AccountRepository;
import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.repository.Type_ChartRepository;
import com.TFG.app.backend.user.UserTestDataBuilder;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@ActiveProfiles("test")
public class AccountIntegrationTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Type_ChartRepository typeChartRepository;

    @Test
    public void testSaveAccount() {
        Type_Chart typeChart = new Type_Chart();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().
        withTypeChart(typeChart).
        build();
        userRepository.save(user);

        Account account = new Account();
        account.setUser(user);
        account.setBankName("Unicaja");
        account.setName("Cuenta Ahorros");
        account.setNumber("123456789");
        account.setTrueLayerId("tl_id_123456");
        accountRepository.save(account);

        assertThat(accountRepository.existsByUser(user)).isTrue();
    }

    @Test
    public void testSaveDuplicateTrueLayerThrowsException() {
        Type_Chart typeChart = new Type_Chart();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().
        withTypeChart(typeChart).
        build();
        userRepository.save(user);

        Account account1 = new Account();
        account1.setUser(user);
        account1.setName("Cuenta de Ahorros");
        account1.setBankName("Unicaja");
        account1.setNumber("123456789");
        account1.setTrueLayerId("tl_id_123456");
        accountRepository.save(account1);

        Account account2 = new Account();
        account2.setUser(user);
        account2.setName("Cuenta de Ahorros 2");
        account2.setBankName("Santander");
        account2.setNumber("123456789");
        account2.setTrueLayerId("tl_id_123456");

        assertThrows(DataIntegrityViolationException.class, () -> { accountRepository.saveAndFlush(account2); });
    }
    
}