package com.TFG.app.backend.infraestructure.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;
import com.TFG.app.backend.periodic_spending.service.Periodic_SpendingService;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.category.entity.Category;

@Component
public class CategoryMonthlyJob implements Job {

    private final Periodic_SpendingService periodicSpendingService;
    private final SpendingService spendingService;
    public CategoryMonthlyJob(Periodic_SpendingService periodicSpendingService, SpendingService spendingService) {
        this.periodicSpendingService = periodicSpendingService;
        this.spendingService = spendingService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Periodic_Spending> periodicSpendings = periodicSpendingService.getAllValidPeriodicSpendings();
        LocalDate today = LocalDate.now();
        for (Periodic_Spending periodicSpending : periodicSpendings) {
            Spending spending = periodicSpending.getSpending();
            Category category = spending.getCategory();
            BigDecimal amount = spending.getAmount();

            LocalDate originalDate = periodicSpending.getSpending().getDate();
            int dayOfMonth = originalDate.getDayOfMonth();
            int lastDayOfMonth = today.lengthOfMonth();
            int adjustedDay = Math.min(dayOfMonth, lastDayOfMonth);
            LocalDate newSpendingDate = LocalDate.of(today.getYear(), today.getMonth(), adjustedDay);

            Spending newSpending = new Spending();
            newSpending.setAmount(amount);
            newSpending.setCategory(category);
            newSpending.setName(spending.getName());
            newSpending.setDate(newSpendingDate);
            newSpending.setEstablishment(spending.getEstablishment());

            spendingService.createSpending(newSpending);

            periodicSpending.setLastPayment(newSpendingDate);
            periodicSpendingService.createPeriodicSpending(periodicSpending);
        }
    }

}
