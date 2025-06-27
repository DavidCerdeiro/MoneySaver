package com.TFG.app.unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.address.entity.Address;
import com.TFG.app.backend.category.entity.Category;

public class EstablishmentUnitTest {
    @Test
    public void testEstablishmentAttributes() {
        Establishment establishment = new Establishment();
        Address address = new Address();
        Category category = new Category();

        category.setName("Coffee Shop");
        address.setCity("Madrid");
        address.setCountry("Spain");
        establishment.setName("Starbucks");
        establishment.setCategory(category);
        establishment.setAddress(address);

        Assertions.assertEquals("Starbucks", establishment.getName());
        Assertions.assertEquals("Madrid", establishment.getAddress().getCity());
        Assertions.assertEquals("Spain", establishment.getAddress().getCountry());
        Assertions.assertEquals(address, establishment.getAddress());
    }
}
