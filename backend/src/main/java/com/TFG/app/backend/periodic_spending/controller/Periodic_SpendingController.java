package com.TFG.app.backend.periodic_spending.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;
import com.TFG.app.backend.periodic_spending.service.Periodic_SpendingService;

@RestController
@RequestMapping("/api/periodic-spendings")
public class Periodic_SpendingController {
    private final Periodic_SpendingService periodicSpendingService;

    public Periodic_SpendingController(Periodic_SpendingService periodicSpendingService) {
        this.periodicSpendingService = periodicSpendingService;
    }

    @PostMapping
    public Periodic_Spending create(Periodic_Spending periodicSpending) {
        return periodicSpendingService.createPeriodicSpending(periodicSpending);
    }    
}
