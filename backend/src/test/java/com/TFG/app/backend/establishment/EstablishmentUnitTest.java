package com.TFG.app.backend.establishment;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.TFG.app.backend.establishment.entity.Establishment;
public class EstablishmentUnitTest {
    @Test
    public void testNewEstablishment() {
        Establishment establishment = new Establishment();
        establishment.setName("El Corte Inglés");

        assertEquals("El Corte Inglés", establishment.getName());
    }
}
