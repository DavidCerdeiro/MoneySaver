package com.TFG.app.backend.spending.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.type_periodic.service.Type_PeriodicService;
import com.TFG.app.backend.periodic_spending.service.Periodic_SpendingService;
import com.TFG.app.backend.establishment.service.EstablishmentService;
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
import java.util.ArrayList;
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
    private final EstablishmentService establishmentService;

    public SpendingController(SpendingService spendingService, UserService userService, CategoryService categoryService, Type_PeriodicService typePeriodicService, Periodic_SpendingService periodicSpendingService, EstablishmentService establishmentService) {
        this.spendingService = spendingService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.typePeriodicService = typePeriodicService;
        this.periodicSpendingService = periodicSpendingService;
        this.establishmentService = establishmentService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSpending(@RequestBody AddSpendingRequest spendingRequest) {
        Spending spending = new Spending();
        Category category = categoryService.getCategoryFromId(spendingRequest.getIdCategory());
        User user = userService.getUserById(spendingRequest.getIdUser());
        if(spendingRequest.getEstablishment() != null) {
            System.out.println("Establishment: " + spendingRequest.getEstablishment().getName());
            if(spendingRequest.getEstablishment().getId() == 0) { // A new establishment
                spending.setEstablishment(establishmentService.newEstablishment(spendingRequest.getEstablishment().getName(), spendingRequest.getEstablishment().getCountry(), spendingRequest.getEstablishment().getCity()));
            } else {
                spending.setEstablishment(establishmentService.findById(spendingRequest.getEstablishment().getId()));
            }
        }
        spending.setName(spendingRequest.getName());
        spending.setUser(user);
        spending.setCategory(category);
        

        spending.setAmount(BigDecimal.valueOf(spendingRequest.getAmount()).setScale(2, RoundingMode.HALF_UP));
        
        // Convert date string to Date object
        LocalDate localDate = LocalDate.parse(spendingRequest.getDate());
        spending.setDate(localDate);
        spending.setIsPeriodic(spendingRequest.isPeriodic());

        Spending savedSpending = spendingService.createSpending(spending);
        if(savedSpending != null){
            if(spendingRequest.isPeriodic()) {
                Periodic_Spending periodicSpending = new Periodic_Spending();
                periodicSpending.setSpending(spending);
                periodicSpending.setTypePeriodic(typePeriodicService.getTypePeriodicById(spendingRequest.getTypePeriodic()));
                localDate = LocalDate.parse(spendingRequest.getExpirationDate());
                periodicSpending.setExpiration(localDate);
                periodicSpending.setLastPayment(localDate);
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
