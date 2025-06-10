package com.TFG.app.backend.bill.controller;

import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.bill.entity.Bill;
import com.TFG.app.backend.bill.service.BillService;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping("/create")
    public Bill createBill(@RequestBody Bill bill) {
        return billService.createBill(bill);
    }
}
