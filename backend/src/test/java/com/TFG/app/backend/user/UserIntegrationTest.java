package com.TFG.app.backend.user;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import com.TFG.app.backend.type_chart.TypeChartTestDataBuilder;
import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.repository.Type_ChartRepository;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.repository.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Type_ChartRepository typeChartRepository;

    @Test
    public void testUserCreation() {
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);
        
        User user = new User();

        user.setName("Iker");
        user.setSurname("Casillas");
        user.setEmail("iker.casillas@gmail.com");
        user.setPassword("password");
        user.setTypeChart(typeChart);
        userRepository.save(user);

        Assertions.assertTrue(userRepository.existsByEmail("iker.casillas@gmail.com"));
    }

    @Test
    public void testDuplicateEmail(){
        Type_Chart typeChart = new TypeChartTestDataBuilder().build();
        typeChartRepository.save(typeChart);

        User user1 = new User();
        user1.setName("Iker");
        user1.setSurname("Casillas");
        user1.setEmail("iker.casillas@gmail.com");
        user1.setPassword("password");
        user1.setTypeChart(typeChart);
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("Cristiano");
        user2.setSurname("Ronaldo");
        user2.setEmail("iker.casillas@gmail.com");
        user2.setPassword("password");
        user2.setTypeChart(typeChart);

        assertThrows(DataIntegrityViolationException.class, () -> { userRepository.saveAndFlush(user2); });
    }
}
