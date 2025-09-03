package com.TFG.app.backend.bill;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import com.TFG.app.backend.bill.entity.Bill;
import com.TFG.app.backend.spending.entity.Spending;

public class BillUnitTest {
    @Test
    public void testBillAttributes() {
        Bill bill = new Bill();
        Spending spending = new Spending();

        spending.setName("Electricity Bill");
        bill.setFileRoute("/path/to/bill.pdf");
        bill.setSpending(spending);

        Assertions.assertEquals("/path/to/bill.pdf", bill.getFileRoute());
        Assertions.assertEquals("Electricity Bill", bill.getSpending().getName());

    }
}
