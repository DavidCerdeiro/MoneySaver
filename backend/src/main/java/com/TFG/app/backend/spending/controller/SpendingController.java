package com.TFG.app.backend.spending.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.type_periodic.service.Type_PeriodicService;
import com.TFG.app.backend.periodic_spending.service.Periodic_SpendingService;
import com.TFG.app.backend.user.service.UserService;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.category.service.CategoryService;
import com.TFG.app.backend.periodic_spending.entity.Periodic_Spending;
import com.TFG.app.backend.spending.dto.AddSpendingRequest;
import com.TFG.app.backend.spending.dto.AllSpendingFromUserMonthAndYearResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.TFG.app.backend.spending.dto.SpendingResponse;
@RestController
@RequestMapping("/api/spendings")
public class SpendingController {
    private final SpendingService spendingService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final Type_PeriodicService typePeriodicService;
    private final Periodic_SpendingService periodicSpendingService;

    public SpendingController(SpendingService spendingService, UserService userService, CategoryService categoryService, Type_PeriodicService typePeriodicService, Periodic_SpendingService periodicSpendingService) {
        this.spendingService = spendingService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.typePeriodicService = typePeriodicService;
        this.periodicSpendingService = periodicSpendingService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSpending(@RequestBody AddSpendingRequest spendingRequest) {
        Spending spending = new Spending();
        Category category = categoryService.getCategoryFromUserAndId(spendingRequest.getIdUser(), spendingRequest.getIdCategory());
        User user = userService.getUserById(spendingRequest.getIdUser());

        spending.setName(spendingRequest.getName());
        spending.setUser(user);
        spending.setCategory(category);
        category.setTotalSpending(category.getTotalSpending().add(BigDecimal.valueOf(spendingRequest.getAmount()).setScale(2, RoundingMode.HALF_UP)));

        spending.setAmount(BigDecimal.valueOf(spendingRequest.getAmount()).setScale(2, RoundingMode.HALF_UP));
        categoryService.updateCategory(category);
        // Convert date string to Date object
        LocalDate localDate = LocalDate.parse(spendingRequest.getDate());
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        spending.setDate(date);
        spending.setIsPeriodic(spendingRequest.isPeriodic());

        Spending savedSpending = spendingService.createSpending(spending);
        if(savedSpending != null){
            if(spendingRequest.isPeriodic()) {
                Periodic_Spending periodicSpending = new Periodic_Spending();
                periodicSpending.setSpending(spending);
                periodicSpending.setTypePeriodic(typePeriodicService.getTypePeriodicById(spendingRequest.getTypePeriodic()));
                localDate = LocalDate.parse(spendingRequest.getExpirationDate());
                date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                periodicSpending.setExpiration(date);
                periodicSpending.setLastExecution(date);
                periodicSpendingService.createPeriodicSpending(periodicSpending);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<AllSpendingFromUserMonthAndYearResponse> AllCategoriesFromUser(@RequestParam("idUser") Long idUser, @RequestParam("month") int month, @RequestParam("year") int year) {
        List<Spending> spendings = spendingService.getAllSpendingsByUserMonthAndYear(idUser.intValue(), month, year);
        List<SpendingResponse> spendingResponses = new ArrayList<>();
        for (Spending spending : spendings) {
            SpendingResponse spendingResponse = new SpendingResponse(
                spending.getId(),
                spending.getName(),
                spending.getAmount(),
                spending.getDate(),
                spending.getCategory().getName(),
                spending.getCategory().getIcon(),
                spending.getIsPeriodic()
            );
            
            spendingResponses.add(spendingResponse);
        }
        return new ResponseEntity<>(new AllSpendingFromUserMonthAndYearResponse(spendingResponses), HttpStatus.OK);
    }
}
