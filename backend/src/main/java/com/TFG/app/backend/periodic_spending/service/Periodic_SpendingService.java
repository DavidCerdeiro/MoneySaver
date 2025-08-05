package com.TFG.app.backend.periodic_spending.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.periodic_spending.repository.Periodic_SpendingRepository;
import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
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

    public List<Periodic_Spending> getAllValidPeriodicSpendings() {
        LocalDate today = LocalDate.now();
        List<Periodic_Spending> periodicSpendings = periodicSpendingRepository.findByExpirationAfter(new Date());
        Iterator<Periodic_Spending> iterator = periodicSpendings.iterator();
        // Iterate through the list and check if the last execution date is valid based on the type of periodic spending
        while (iterator.hasNext()) {
            Periodic_Spending ps = iterator.next();
            LocalDate lastExecution = ps.getLastExecution().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
            int monthsBetween = (today.getYear() - lastExecution.getYear()) * 12 + (today.getMonthValue() - lastExecution.getMonthValue());
            boolean isValid;
            switch (ps.getTypePeriodic().getId()) {
                case 1: // Monthly
                    isValid = monthsBetween == 1;
                    break;

                case 2: // Quarterly
                    isValid = monthsBetween == 3;
                    break;

                case 3: // Annually
                    isValid = monthsBetween == 12;
                    break;
                default:
                    isValid = false;
                    break;
            }
            if(isValid) {
                ps.setLastExecution(Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                periodicSpendingRepository.save(ps); // Update last execution date
            }else{
                iterator.remove(); // Remove invalid periodic spendings
            }
        }
        return periodicSpendings;
    }
    
}
