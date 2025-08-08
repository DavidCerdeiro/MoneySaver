package com.TFG.app.backend.spending.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.spending.repository.SpendingRepository;
import com.TFG.app.backend.spending.entity.Spending;
import java.util.List;
import java.math.BigDecimal;
@Service
public class SpendingService {
    private final SpendingRepository spendingRepository;

    public SpendingService(SpendingRepository spendingRepository) {
        this.spendingRepository = spendingRepository;
    }

    public Spending createSpending(Spending spending) {
        return spendingRepository.save(spending);
    }

    public List<Spending> getAllSpendingsByUserMonthAndYear(int userId, int month, int year) {
        return spendingRepository.findAllByUserIdAndMonthAndYear(userId, month, year);
    }

    public BigDecimal getTotalAmountByCategory(int categoryId) {
        List<Spending> spendings = spendingRepository.findAllByCategoryId(categoryId);
        BigDecimal total = BigDecimal.ZERO;
        for (Spending spending : spendings) {
            total = total.add(spending.getAmount());
        }
        return total;
    }

}
