package com.TFG.app.unit_tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.TFG.app.backend.type_periodic.entity.Type_Periodic;
public class Type_PeriodicUnitTest {
    @Test
    public void testTypePeriodicAttributes() {
        Type_Periodic typePeriodic = new Type_Periodic();
        typePeriodic.setName("Monthly");

        Assertions.assertEquals("Monthly", typePeriodic.getName());
    }
}
