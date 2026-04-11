package com.TFG.app.backend.periodic_spending.job;

import java.time.LocalDate;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;
import com.TFG.app.backend.periodic_spending.service.Periodic_SpendingService;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.spending.service.SpendingService;

@Component
public class PeriodicSpendingJob implements Job {

    @Autowired
    private Periodic_SpendingService periodicSpendingService;

    @Autowired
    private SpendingService spendingService;

    @Override
    @Transactional
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Ejecutando Job de Gastos Periódicos con Quartz...");
        List<Periodic_Spending> pendingSpendings = periodicSpendingService.getAllValidPeriodicSpendings();
        LocalDate today = LocalDate.now();

        for (Periodic_Spending periodicSpending : pendingSpendings) {
            
            // Comprobación de seguridad
            LocalDate lastPayment = periodicSpending.getLastPayment();
            if (lastPayment != null && lastPayment.getMonth() == today.getMonth() && lastPayment.getYear() == today.getYear()) {
                continue; 
            }

            Spending baseSpending = periodicSpending.getSpending();
            LocalDate originalDate = baseSpending.getDate();
            int adjustedDay = Math.min(originalDate.getDayOfMonth(), today.lengthOfMonth());
            LocalDate newSpendingDate = LocalDate.of(today.getYear(), today.getMonth(), adjustedDay);

            Spending newSpending = new Spending();
            newSpending.setAmount(baseSpending.getAmount());
            newSpending.setCategory(baseSpending.getCategory());
            newSpending.setName(baseSpending.getName());
            newSpending.setDate(newSpendingDate);
            newSpending.setEstablishment(baseSpending.getEstablishment());

            spendingService.createSpending(newSpending);

            periodicSpending.setLastPayment(newSpendingDate);
            periodicSpendingService.updatePeriodicSpending(periodicSpending);
        }
    }
}