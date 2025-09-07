package com.TFG.app.backend.infraestructure.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;
import com.TFG.app.backend.periodic_spending.service.Periodic_SpendingService;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.spending.service.SpendingService;

@Component
public class PeriodicSpendingStartupChecker {

    private final Periodic_SpendingService periodicSpendingService;
    private final SpendingService spendingService;

    public PeriodicSpendingStartupChecker(Periodic_SpendingService periodicSpendingService, SpendingService spendingService) {
        this.periodicSpendingService = periodicSpendingService;
        this.spendingService = spendingService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void checkPendingPeriodicSpendings() {
        List<Periodic_Spending> pendingSpendings = periodicSpendingService.getAllValidPeriodicSpendings();

        for (Periodic_Spending periodicSpending : pendingSpendings) {
            Spending baseSpending = periodicSpending.getSpending();
            LocalDate today = LocalDate.now();

            // Calcular la fecha exacta del gasto que falta
            LocalDate originalDate = baseSpending.getDate();
            int dayOfMonth = originalDate.getDayOfMonth();
            int lastDayOfMonth = today.lengthOfMonth();
            int adjustedDay = Math.min(dayOfMonth, lastDayOfMonth);
            LocalDate newSpendingDate = LocalDate.of(today.getYear(), today.getMonth(), adjustedDay);

            Spending newSpending = new Spending();
            newSpending.setAmount(baseSpending.getAmount());
            newSpending.setCategory(baseSpending.getCategory());
            newSpending.setName(baseSpending.getName());
            newSpending.setDate(newSpendingDate);
            newSpending.setEstablishment(baseSpending.getEstablishment());

            spendingService.createSpending(newSpending);

            // Guardar el último pago en la fecha real
            periodicSpending.setLastPayment(newSpendingDate);
            periodicSpendingService.updatePeriodicSpending(periodicSpending);
        }
    }
}

