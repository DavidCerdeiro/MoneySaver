package com.TFG.app.backend.bill.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TFG.app.backend.bill.entity.Bill;
import com.TFG.app.backend.bill.service.BillService;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public Bill create(@RequestBody Bill bill) {
        return billService.createBill(bill);
    }
}
