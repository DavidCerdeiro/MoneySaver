package com.TFG.app.unit_tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.type_periodic.entity.Type_Periodic;
public class Periodic_SpendingUnitTest {
    @Test
    public void testPeriodicSpendingAttributes() {
        Periodic_Spending periodicSpending = new Periodic_Spending();
        Type_Periodic typePeriodic = new Type_Periodic();
        Spending spending = new Spending();

        spending.setName("Netflix");
        typePeriodic.setName("Monthly");
        periodicSpending.setExpiration(LocalDate.now().plusMonths(1));
        periodicSpending.setTypePeriodic(typePeriodic);
        periodicSpending.setSpending(spending);
        
        Assertions.assertEquals("Netflix", periodicSpending.getSpending().getName());
        Assertions.assertEquals("Monthly", periodicSpending.getTypePeriodic().getName());
        Assertions.assertNotNull(periodicSpending.getExpiration());
    }
}
