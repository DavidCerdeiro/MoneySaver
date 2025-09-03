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
    
    /**
     * Endpoint to extract transactions from an account by TrueLayer
     * @param token
     * @param request
     * @return
     * - 201: Created AddTransactionResponse 
     * - 400: Bad Request if the request is invalid
     * - 401: Unauthorized if token is missing or invalid
     * - 404: Not Found if the user is not found
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
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
                .host("api.truelayer-sandbox.com") // Sandbox host
                .path("/data/v1/accounts/{accountId}/transactions") // Path with placeholder for accountId
                .queryParam("from", request.getMinDate()) 
                .queryParam("to", request.getMaxDate())   // Dates min and max
                .build(request.getAccount().getAccountCode())) // Build URI with accountId
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // Authorization header
            .retrieve() // Execute request
            .bodyToMono(String.class) // Convert response to String
            .block();
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode transactionsJson = mapper.readTree(body);

        List<Object[]> transactionPairs = new ArrayList<>();
        ExtractTransactionsResponse response = new ExtractTransactionsResponse();

        for (JsonNode tran : transactionsJson.get("results")) {
            if (!tran.has("transaction_type") || !"DEBIT".equalsIgnoreCase(tran.get("transaction_type").asText())) {
                continue; // skip if not DEBIT
            }
            String transactionId = tran.get("transaction_id").asText();
            if(transactionService.existsByTransactionCode(transactionId)) {
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
                   spending.setEstablishment(new Establishment(0, establishmentName));
               }
            }else{
                spending.setEstablishment(new Establishment(0, ""));
            }

            transactionPairs.add(new Object[]{new TransactionExtractedResponse(tran.get("transaction_id").asText(), request.getAccount()), spending});
        }
        
        response.setTransactions(transactionPairs);
        return ResponseEntity.ok(response);
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

        List<Account> accounts = accountService.findByUser(user);
        List<TransactionResponse> response = new ArrayList<>();
        for (Account account : accounts) {
            List<Transaction> transactions = transactionService.getAllByAccountAndMonth(account.getId(), month, year);
            for (Transaction transaction : transactions) {
                Spending spending = transaction.getSpending();
                response.add(new TransactionResponse(transaction.getId(), spending.getName(), spending.getAmount(), spending.getDate(), account.getName(), account.getNumber(), spending.getCategory().getIcon(), spending.getCategory().getName(), spending.getEstablishment() != null ? spending.getEstablishment().getName() : ""));
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
