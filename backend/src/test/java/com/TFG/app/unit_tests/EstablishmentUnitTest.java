package com.TFG.app.unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.region.entity.Region;
import com.TFG.app.backend.category.entity.Category;

public class EstablishmentUnitTest {
    @Test
    public void testEstablishmentAttributes() {
        Establishment establishment = new Establishment();
        Region region = new Region();
        Category category = new Category();

        category.setName("Coffee Shop");
        region.setCity("Cadiz");
        region.setCountry("Spain");
        establishment.setName("Starbucks");
        establishment.setCategory(category);
        establishment.setRegion(region);

        Assertions.assertEquals("Starbucks", establishment.getName());
        Assertions.assertEquals("Cadiz", establishment.getRegion().getCity());
        Assertions.assertEquals("Spain", establishment.getRegion().getCountry());
        Assertions.assertEquals(region, establishment.getRegion());
    }
}
