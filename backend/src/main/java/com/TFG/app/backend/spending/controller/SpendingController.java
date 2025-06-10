package com.TFG.app.backend.spending.controller;

import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.spending.entity.Spending;

@RestController
@RequestMapping("/api/spendings")
public class SpendingController {
    private final SpendingService spendingService;

    public SpendingController(SpendingService spendingService) {
        this.spendingService = spendingService;
    }

    @PostMapping("/create")
    public Spending create(Spending spending) {
        return spendingService.createSpending(spending);
    }
}
