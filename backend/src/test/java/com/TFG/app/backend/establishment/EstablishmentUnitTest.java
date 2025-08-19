package com.TFG.app.backend.establishment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.region.entity.Region;


public class EstablishmentUnitTest {
    @Test
    public void testEstablishmentAttributes() {
        Establishment establishment = new Establishment();
        Region region = new Region();

        region.setCity("Cadiz");
        region.setCountry("Spain");
        establishment.setName("Tinoco");

        establishment.setRegion(region);

        Assertions.assertEquals("Tinoco", establishment.getName());
        Assertions.assertEquals("Cadiz", establishment.getRegion().getCity());
        Assertions.assertEquals("Spain", establishment.getRegion().getCountry());
        Assertions.assertEquals(region, establishment.getRegion());
    }
}
