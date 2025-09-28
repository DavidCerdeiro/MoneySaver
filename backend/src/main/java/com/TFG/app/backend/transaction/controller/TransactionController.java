package com.TFG.app.backend.transaction.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.account.service.AccountService;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.category.service.CategoryService;
import com.TFG.app.backend.establishment.service.EstablishmentService;
import com.TFG.app.backend.infraestructure.config.JwtService;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.transaction.dto.TransactionResponse;
import com.TFG.app.backend.transaction.dto.AddTransactionRequest;
import com.TFG.app.backend.transaction.dto.AddTransactionResponse;
import com.TFG.app.backend.transaction.entity.Transaction;
import com.TFG.app.backend.transaction.service.TransactionService;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.service.UserService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final JwtService jwtService;
    private final UserService userService;
    private final EstablishmentService establishmentService;
    private final SpendingService spendingService;
    private final CategoryService categoryService;
    private final AccountService accountService;
    @Value("${truelayer.client-id}")
    private String truelayerClientId;

    @Value("${truelayer.client-secret}")
    private String truelayerSecret;

    @Value("${truelayer.redirect-url}")
    private String truelayerRedirectUrl;

    public TransactionController(TransactionService transactionService, JwtService jwtService, UserService userService, EstablishmentService establishmentService, SpendingService spendingService, CategoryService categoryService, AccountService accountService) {
        this.transactionService = transactionService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.establishmentService = establishmentService;
        this.categoryService = categoryService;
        this.spendingService = spendingService;
        this.accountService = accountService;
    }

    /**
     * Endpoint to add a new transaction
     * @param token
     * @param request
     * @return
     * - 201: Created AddTransactionResponse if the transaction is successfully added
     * - 400: Bad Request if the request is invalid
     * - 401: Unauthorized if token is missing or invalid
     * - 404: Not Found if the user is not found
     */
    @PostMapping
    public ResponseEntity<AddTransactionResponse> postTransaction(@CookieValue(name = "accessToken", required = false) String token, @RequestBody AddTransactionRequest request) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Spending spending = new Spending();
        if(request.getSpending().getEstablishment() != null) {
            if(request.getSpending().getEstablishment().getId() == 0 && request.getSpending().getEstablishment().getName() != null) {
                if(request.getSpending().getEstablishment().getName() != "") {
                    String nameEstablishment = request.getSpending().getEstablishment().getName().toUpperCase();
                    spending.setEstablishment(establishmentService.newEstablishment(nameEstablishment));
                }
            } else {
                spending.setEstablishment(establishmentService.findById(request.getSpending().getEstablishment().getId()));
            }
        }
        Category category = categoryService.getCategoryFromId(request.getSpending().getIdCategory());
        spending.setCategory(category);
        spending.setName(request.getSpending().getName());
        spending.setAmount(BigDecimal.valueOf(request.getSpending().getAmount()).setScale(2, RoundingMode.HALF_UP));
        spending.setDate(LocalDate.parse(request.getSpending().getDate()));
        spendingService.createSpending(spending);

        Transaction transaction = new Transaction();
        transaction.setAccount(accountService.findById(request.getTransaction().getAccount().getId()));
        transaction.setTransactionCode(request.getTransaction().getTransactionCode());
        transaction.setSpending(spending);
        transactionService.createTransaction(transaction);

        return ResponseEntity.ok(new AddTransactionResponse(transaction.getSpending().getName(), transaction.getSpending().getAmount()));
    }

    /**
     * Endpoint to get all transactions for a user
     * @param token
     * @param month
     * @param year
     * @return
     * - 200: OK with a list of TransactionResponse if the transactions are successfully retrieved
     * - 401: Unauthorized if token is missing or invalid
     * - 404: Not Found if the user is not found
     */
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions(@CookieValue(name = "accessToken", required = false) String token, @RequestParam("month") int month,
            @RequestParam("year") int year) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        List<Transaction> transactions = transactionService.getAllByUserAndMonth(user, month, year);
        List<TransactionResponse> response = new ArrayList<>();
        for (Transaction tr : transactions) {
            Spending spending = tr.getSpending();
            Account account = accountService.findById(tr.getAccount().getId());
            response.add(new TransactionResponse(tr.getId(), spending.getName(), spending.getAmount(), spending.getDate(), account.getName(), account.getNumber(), spending.getCategory().getIcon(), spending.getCategory().getName(), spending.getEstablishment() != null ? spending.getEstablishment().getName() : ""));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
