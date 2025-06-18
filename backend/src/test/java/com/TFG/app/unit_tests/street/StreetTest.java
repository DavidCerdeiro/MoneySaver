package com.TFG.app.unit_tests.street;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import com.TFG.app.backend.street.entity.Street;

public class StreetTest {
    @Test
    public void testStreetAttributes() {
        Street street = new Street();

        street.setName("Calle Teniente Andujar");
        street.setNumber(1);
    

        Assertions.assertEquals("Calle Teniente Andujar", street.getName());
        Assertions.assertEquals(1, street.getNumber());
    } 
}
