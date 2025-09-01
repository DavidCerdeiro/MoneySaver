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
import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.establishment.service.EstablishmentService;
import com.TFG.app.backend.infraestructure.config.AccessTokenTrueLayer;
import com.TFG.app.backend.infraestructure.config.JwtService;
import com.TFG.app.backend.spending.dto.SpendingTransactionResponse;
import com.TFG.app.backend.spending.entity.Spending;
import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.transaction.dto.ExtractTransactionsResponse;
import com.TFG.app.backend.transaction.dto.TransactionExtractedResponse;
import com.TFG.app.backend.transaction.dto.TransactionResponse;
import com.TFG.app.backend.transaction.dto.AddTransactionRequest;
import com.TFG.app.backend.transaction.dto.AddTransactionResponse;
import com.TFG.app.backend.transaction.dto.ExtractTransactionsRequest;
import com.TFG.app.backend.transaction.entity.Transaction;
import com.TFG.app.backend.transaction.service.TransactionService;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final JwtService jwtService;
    private final UserService userService;
    private final WebClient webClient;
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
        this.webClient = WebClient.builder().build();
        this.establishmentService = establishmentService;
        this.categoryService = categoryService;
        this.spendingService = spendingService;
        this.accountService = accountService;
    }
    
    @PostMapping("/extract")
    public ResponseEntity<ExtractTransactionsResponse> extract(@CookieValue(name = "accessToken", required = false) String token, @RequestBody ExtractTransactionsRequest request) throws JsonMappingException, JsonProcessingException{

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

        String clientId = truelayerClientId;
        String clientSecret = truelayerSecret;
        String redirectUri = truelayerRedirectUrl;

        String accessToken = new AccessTokenTrueLayer().getAccessToken(clientId, clientSecret, redirectUri, request.getCode());

        if (accessToken == null) {
            System.out.println("Access token es nulo");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        
        String body = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host("api.truelayer-sandbox.com") // ✅ Usa el host del sandbox
                .path("/data/v1/accounts/{accountId}/transactions") // Path con placeholder
                .queryParam("from", request.getMinDate()) // Añade parámetro 'from'
                .queryParam("to", request.getMaxDate())   // Añade parámetro 'to'
                .build(request.getAccount().getTrueLayerId())) // Asigna el valor al placeholder {accountId}
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // Añade la cabecera de autorización
            .retrieve() // Ejecuta la petición
            .bodyToMono(String.class) // Convierte la respuesta a un String
            .block();
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode transactionsJson = mapper.readTree(body);

        List<Object[]> transactionPairs = new ArrayList<>();
        ExtractTransactionsResponse response = new ExtractTransactionsResponse();

        for (JsonNode tran : transactionsJson.get("results")) {
            if (!tran.has("transaction_type") || !"DEBIT".equalsIgnoreCase(tran.get("transaction_type").asText())) {
                continue; // saltamos si no es DEBIT
            }
            String transactionId = tran.get("transaction_id").asText();
            if(transactionService.existsByTrueLayerId(transactionId)) {
                System.out.println("La transacción con ID " + transactionId + " ya existe.");
                continue;
            }
            SpendingTransactionResponse spending = new SpendingTransactionResponse();
            spending.setName(tran.has("description") && !tran.get("description").isNull() ? tran.get("description").asText() : "");
            spending.setAmount(BigDecimal.valueOf(tran.get("amount").asDouble() * -1));
            spending.setDate(java.time.LocalDate.parse(tran.get("timestamp").asText().substring(0, 10)));
            String establishmentName = (tran.has("merchant_name") && !tran.get("merchant_name").isNull()) ? tran.get("merchant_name").asText() : "";
            if(establishmentName != null && !establishmentName.isEmpty()) {
               Establishment est = establishmentService.findByName(establishmentName);
               if (est != null) {
                   spending.setEstablishment(est);
               } else {
                   spending.setEstablishment(new Establishment(0, user, establishmentName));
               }
            }else{
                spending.setEstablishment(new Establishment(0, user, ""));
            }

            transactionPairs.add(new Object[]{new TransactionExtractedResponse(tran.get("transaction_id").asText(), request.getAccount()), spending});
        }
        
        response.setTransactions(transactionPairs);
        System.out.println("Transacciones extraídas: " + response.getTransactions().size());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<AddTransactionResponse> addTransaction(@CookieValue(name = "accessToken", required = false) String token, @RequestBody AddTransactionRequest request) {
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
                    String nameEstablishment = request.getSpending().getEstablishment().getName().substring(0, 1).toUpperCase()
                        + request.getSpending().getEstablishment().getName().substring(1).toLowerCase();   
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
        spending.setIsPeriodic(false);
        spendingService.createSpending(spending);

        Transaction transaction = new Transaction();
        transaction.setAccount(accountService.findById(request.getTransaction().getAccount().getId()));
        transaction.setTrueLayerId(request.getTransaction().getTrueLayerId());
        transaction.setSpending(spending);
        transactionService.createTransaction(transaction);

        return ResponseEntity.ok(new AddTransactionResponse(transaction.getSpending().getName(), transaction.getSpending().getAmount()));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<TransactionResponse>> getAllUserTransactions(@CookieValue(name = "accessToken", required = false) String token, @RequestParam("month") int month,
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

        List<Account> accounts = accountService.findByUser(user);
        List<TransactionResponse> response = new ArrayList<>();
        for (Account account : accounts) {
            List<Transaction> transactions = transactionService.getAllByAccountAndMonth(account.getId(), month, year);
            for (Transaction transaction : transactions) {
                Spending spending = transaction.getSpending();
                response.add(new TransactionResponse(transaction.getId(), spending.getName(), spending.getAmount(), spending.getDate(), account.getName(), account.getNumber(), spending.getCategory().getIcon(), spending.getCategory().getName(), spending.getEstablishment().getName()));
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
