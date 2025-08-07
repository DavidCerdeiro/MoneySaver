package com.TFG.app.unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.TFG.app.backend.region.entity.Region;

public class RegionUnitTest {
    @Test
    public void testAddressAttributes() {
        Region region = new Region();


        region.setCity("Madrid");
        region.setCountry("Spain");

        Assertions.assertEquals("Madrid", region.getCity());
        Assertions.assertEquals("Spain", region.getCountry());
    }
}
