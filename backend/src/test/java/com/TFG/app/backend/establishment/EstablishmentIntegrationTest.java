package com.TFG.app.backend.establishment;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.establishment.repository.EstablishmentRepository;
import com.TFG.app.backend.type_chart.entity.Type_Chart;
import com.TFG.app.backend.type_chart.repository.Type_ChartRepository;
import com.TFG.app.backend.user.UserTestDataBuilder;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.repository.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
public class EstablishmentIntegrationTest {

    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Autowired
    private Type_ChartRepository typeChartRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testNewEstablishment() {
        Type_Chart typeChart = new Type_Chart();
        typeChartRepository.save(typeChart);

        User user = new UserTestDataBuilder().
        withTypeChart(typeChart).
        build();
        userRepository.save(user);
        
        Establishment establishment = new Establishment();

        establishment.setName("El Corte Inglés");

        establishmentRepository.save(establishment);

        Assertions.assertEquals("El Corte Inglés", establishmentRepository.findByName("El Corte Inglés").getName());
    }

    @Test
    public void testDuplicateEstablishment(){
        Establishment establishment = new Establishment();
        Establishment establishment2 = new Establishment();

        establishment.setName("El Corte Inglés");
        establishmentRepository.save(establishment);

        establishment2.setName("El Corte Inglés");

        assertThrows(DataIntegrityViolationException.class, () -> { establishmentRepository.saveAndFlush(establishment2); });
    }
}