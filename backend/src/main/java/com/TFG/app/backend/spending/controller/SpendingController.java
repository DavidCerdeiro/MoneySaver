package com.TFG.app.backend.spending.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.spending.entity.Spending;

@RestController
@RequestMapping("/api/spendings")
public class SpendingController {
    private final SpendingService spendingService;

    public SpendingController(SpendingService spendingService) {
        this.spendingService = spendingService;
    }

    @PostMapping
    public Spending create(Spending spending) {
        return spendingService.createSpending(spending);
    }
}
