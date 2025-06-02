package com.TFG.app.backend.spending.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.spending.repository.SpendingRepository;
import com.TFG.app.backend.spending.entity.Spending;

@Service
public class SpendingService {
    private final SpendingRepository spendingRepository;

    public SpendingService(SpendingRepository spendingRepository) {
        this.spendingRepository = spendingRepository;
    }

    public Spending createSpending(Spending spending) {
        return spendingRepository.save(spending);
    }
}
