package com.TFG.app.unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import com.TFG.app.backend.address.entity.Address;
import com.TFG.app.backend.street.entity.Street;

public class AddressUnitTest {
    @Test
    public void testAddressAttributes() {
        Address address = new Address();
        Street  street = new Street();

        street.setName("Gran Via");
        address.setCity("Madrid");;
        address.setCountry("Spain");
        address.setStreet(street);

        Assertions.assertEquals("Madrid", address.getCity());
        Assertions.assertEquals("Spain", address.getCountry());
        Assertions.assertEquals(street, address.getStreet());
    }    
}
