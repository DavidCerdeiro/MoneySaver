package com.TFG.app.backend.spending.service;

import org.springframework.stereotype.Service;

import com.TFG.app.backend.spending.repository.SpendingRepository;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.spending.entity.Spending;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public BigDecimal getTotalAmountMonthlyByCategory(int categoryId, int month, int year) {
        List<Spending> spendings = spendingRepository.getAllByCategoryAndMonth(categoryId, month, year);
        BigDecimal total = BigDecimal.ZERO;
        for (Spending spending : spendings) {
            total = total.add(spending.getAmount());
        }
        return total;
    }

    public LocalDate parseFlexibleDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }

        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("d-M-yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                DateTimeFormatter.ofPattern("d.M.yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
            
            }
        }
        
        return null;
}
    public Spending getSpendingById(Integer id) {
        return spendingRepository.findById(id).orElse(null);
    }

    public Category getCategoryByEstablishment(Integer establishmentId) {
    List<Spending> spendings = spendingRepository.findByEstablishmentAndCategoryNotDeleted(establishmentId);

    if (spendings.isEmpty()) {
        return null;
    }

    if (spendings.size() == 1) {
        return spendings.get(0).getCategory();
    }

    // Count occurrences of each category in order to find the most frequent one
    Map<Category, Long> counts = spendings.stream()
            .collect(Collectors.groupingBy(Spending::getCategory, Collectors.counting()));

    // Get the most frequent category, and in case of a tie, the first one found
    return spendings.stream()
            .map(Spending::getCategory)
            .max(Comparator.comparingLong(counts::get)) 
            .orElse(null);
}
    public List<Establishment> getEstablishmentsOrderedByUsage(User user, List<Establishment> establishments) {
    // Obtener todos los gastos del usuario
        List<Spending> spendings = spendingRepository.findByUserAndCategoryNotDeleted(user.getId());

        if (spendings.isEmpty()) {
            return Collections.emptyList();
        }

        // Contar ocurrencias de cada establecimiento
        Map<Establishment, Long> counts = spendings.stream()
        .filter(s -> s.getEstablishment() != null) // filtrar nulls
        .collect(Collectors.groupingBy(Spending::getEstablishment, Collectors.counting()));


        // Ordenar: primero los que más se usan (según counts)
        establishments.sort(Comparator.comparingLong(
                e -> -counts.getOrDefault(e, 0L) // Negativo para orden descendente
        ));

        return establishments;
    }

}
