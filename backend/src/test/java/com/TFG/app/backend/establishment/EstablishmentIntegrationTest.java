package com.TFG.app.backend.establishment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.establishment.repository.EstablishmentRepository;

@DataJpaTest
@ActiveProfiles("test")
public class EstablishmentIntegrationTest {

    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Test
    public void testNewEstablishment() {
        Establishment establishment = new Establishment();
        establishment.setName("El Corte Inglés");

        establishmentRepository.save(establishment);
        assertEquals("El Corte Inglés", establishmentRepository.findByName("El Corte Inglés").getName());
    }

    @Test
    public void testDuplicateEstablishment(){
        Establishment establishment = new Establishment();
        establishment.setName("El Corte Inglés");
        establishmentRepository.save(establishment);

        Establishment establishment2 = new Establishment();
        establishment2.setName("El Corte Inglés");
        
        assertThrows(DataIntegrityViolationException.class, () -> {
            establishmentRepository.saveAndFlush(establishment2);
        });
    }
}