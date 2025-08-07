package com.TFG.app.backend.infraestructure.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;
import com.TFG.app.backend.periodic_spending.service.Periodic_SpendingService;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.category.entity.Category;
import java.util.Date;

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
        // Lógica para resetear totales aquí
        System.out.println("Reseteando totales de categorías (1er día del mes)");
        
        System.out.println("Sumando gastos periódicos a las categorías.");
        List<Periodic_Spending> periodicSpendings = periodicSpendingService.getAllValidPeriodicSpendings();
        LocalDate today = LocalDate.now();
        for (Periodic_Spending periodicSpending : periodicSpendings) {
            Spending spending = periodicSpending.getSpending();
            Category category = spending.getCategory();
            BigDecimal amount = spending.getAmount();

            LocalDate originalDate = periodicSpending.getSpending().getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
            int dayOfMonth = originalDate.getDayOfMonth();
            int lastDayOfMonth = today.lengthOfMonth();
            int adjustedDay = Math.min(dayOfMonth, lastDayOfMonth);
            LocalDate newSpendingDate = LocalDate.of(today.getYear(), today.getMonth(), adjustedDay);

            Spending newSpending = new Spending();
            newSpending.setAmount(amount);
            newSpending.setCategory(category);
            newSpending.setUser(spending.getUser());
            newSpending.setName(spending.getName());
            newSpending.setDate(Date.from(newSpendingDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            newSpending.setIsPeriodic(true);
            newSpending.setEstablishment(spending.getEstablishment());

            spendingService.createSpending(newSpending);

        }
    }

}
