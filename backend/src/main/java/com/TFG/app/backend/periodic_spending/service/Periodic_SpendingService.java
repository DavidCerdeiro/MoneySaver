package com.TFG.app.backend.periodic_spending.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.periodic_spending.repository.Periodic_SpendingRepository;
import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;

@Service
public class Periodic_SpendingService {
    private final Periodic_SpendingRepository periodicSpendingRepository;

    public Periodic_SpendingService(Periodic_SpendingRepository periodicSpendingRepository) {
        this.periodicSpendingRepository = periodicSpendingRepository;
    }

    public Periodic_Spending createPeriodicSpending(Periodic_Spending periodicSpending) {
        return periodicSpendingRepository.save(periodicSpending);
    }

    public Periodic_Spending getPeriodicSpendingBySpendingId(Integer spendingId) {
        return periodicSpendingRepository.findBySpendingId(spendingId);
    }
}
