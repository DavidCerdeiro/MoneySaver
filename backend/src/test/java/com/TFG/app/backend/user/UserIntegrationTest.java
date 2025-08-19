package com.TFG.app.backend.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
        assertThat(userRepository.existsByEmail("iker.casillas@gmail.com")).isTrue();
    }

}
