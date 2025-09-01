package com.TFG.app.backend.establishment;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.user.entity.User;
public class EstablishmentUnitTest {
    @Test
    public void testNewEstablishment() {
        Establishment establishment = new Establishment();
        User user = new User();
        establishment.setName("El Corte Inglés");
        establishment.setUser(user);

        assertEquals("El Corte Inglés", establishment.getName());
        assertEquals(user, establishment.getUser());
    }
}
