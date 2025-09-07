package com.TFG.app.backend.account.controller;

import com.TFG.app.backend.account.dto.AccountResponse;
import com.TFG.app.backend.account.dto.ExtractAccountsRequest;
import com.TFG.app.backend.account.dto.GetAccountsResponse;
import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.account.service.AccountService;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.establishment.service.EstablishmentService;
import com.TFG.app.backend.infraestructure.config.AccessTokenTrueLayer;
import com.TFG.app.backend.infraestructure.config.JwtService;
import com.TFG.app.backend.spending.dto.SpendingTransactionResponse;
import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.transaction.dto.ExtractTransactionsResponse;
import com.TFG.app.backend.transaction.dto.TransactionExtractedResponse;
import com.TFG.app.backend.transaction.service.TransactionService;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final UserService userService;
    private final WebClient webClient;
    private final EstablishmentService establishmentService;
    private final TransactionService transactionService;
    private final SpendingService spendingService;
    private final CurrencyConversion euroConversion;

    @Value("${truelayer.client-id}")
    private String truelayerClientId;

    @Value("${truelayer.client-secret}")
    private String truelayerSecret;

    @Value("${truelayer.redirect-url}")
    private String truelayerRedirectUrl;

    public AccountController(AccountService accountService, JwtService jwtService, UserService userService, EstablishmentService establishmentService, TransactionService transactionService, SpendingService spendingService, CurrencyConversion euroConversion) {
        this.accountService = accountService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.webClient = WebClient.builder().build();
        this.establishmentService = establishmentService;
        this.euroConversion = euroConversion;
        this.transactionService = transactionService;
        this.spendingService = spendingService;
    }

    /**
     * Endpoint to extract accounts from TrueLayer and save them to the database
     * @param token
     * @param request
     * @return:
     *  - 200: OK List of AccountResponse
     *  - 401: Unauthorized if token is missing or invalid, or if access token from TrueLayer cannot be obtained
     *  - 404: User not found in the database
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @PostMapping
    public ResponseEntity<List<AccountResponse>> postAccounts(@CookieValue(name = "accessToken", required = false) String token, @RequestBody ExtractAccountsRequest request) throws JsonMappingException, JsonProcessingException {
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
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        // Con el token llamar a /accounts
        String accountsUrl = "https://api.truelayer-sandbox.com/data/v1/accounts";
        String body = webClient.get()
                .uri(accountsUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        List<AccountResponse> accountResponses = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode accountsJson = mapper.readTree(body);
        for (JsonNode acc : accountsJson.get("results")) {
            Account account = new Account();
            account.setUser(user);
            account.setBankName(acc.get("provider").get("display_name").asText());
            account.setNumber(acc.get("account_number").get("number").asText());
            account.setName(acc.get("display_name").asText());
            account.setAccountCode(acc.get("account_id").asText());
            if(!accountService.existByUserAndAccountCode(user, account.getAccountCode())) { // Check if account already exists in the database
                accountService.createAccount(account);
                accountResponses.add(new AccountResponse(account.getId(), account.getAccountCode(), account.getName(), account.getNumber(), account.getBankName()));
            }   
        }

        return ResponseEntity.ok(accountResponses);
    }

    /**
     * Endpoint to get all accounts for the authenticated user
     * @param token
     * @return:
     *  - 200: OK GetAccountsResponse
     *  - 401: Unauthorized if token is missing or invalid
     *  - 404: User not found in the database
     */
    @GetMapping
    public ResponseEntity<GetAccountsResponse> getAccounts(@CookieValue(name = "accessToken", required = false) String token) {
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
        return new ResponseEntity<>(new GetAccountsResponse(accounts), HttpStatus.OK);
    }
    
    /**
     * Endpoint to delete an account for the authenticated user
     * @param token
     * @param id
     * @return:
     *  - 204: No Content if account is successfully deleted
     *  - 401: Unauthorized if token is missing or invalid
     *  - 404: User not found in the database or Account not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@CookieValue(name = "accessToken", required = false) String token, @PathVariable("id") Long id) {
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
        
        if(accountService.deleteAccount(id.intValue())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint to extract transactions for a specific account by TrueLayer
     * @param token
     * @param accountId
     * @param from
     * @param to
     * @param code
     * @return:
     *  - 200: OK ExtractTransactionsResponse
     *  - 401: Unauthorized if token is missing or invalid
     *  - 404: User not found in the database or Account not found
     * @throws JsonProcessingException
     */
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<ExtractTransactionsResponse> extractTransactions(
            @CookieValue(name = "accessToken", required = false) String token,
            @PathVariable("accountId") Integer accountId,
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("code") String code
    ) throws JsonProcessingException {
        
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Account account = accountService.findById(accountId);
        if (account == null || !account.getUser().equals(user)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String accessToken = new AccessTokenTrueLayer().getAccessToken(truelayerClientId, truelayerSecret, truelayerRedirectUrl, code);

        if (accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String body = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host("api.truelayer-sandbox.com")
                .path("/data/v1/accounts/{accountId}/transactions")
                .queryParam("from", from)
                .queryParam("to", to)
                .build(account.getAccountCode()))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(String.class)
            .block();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode transactionsJson = mapper.readTree(body);

        List<Object[]> transactionPairs = new ArrayList<>();
        ExtractTransactionsResponse response = new ExtractTransactionsResponse();

        for (JsonNode tran : transactionsJson.get("results")) {
            if (!tran.has("transaction_type") 
                || !"DEBIT".equalsIgnoreCase(tran.get("transaction_type").asText())) {
                continue;
            }

            String transactionId = tran.get("transaction_id").asText();
            if(transactionService.existsByTransactionCode(transactionId)) {
                continue;
            }

            SpendingTransactionResponse spending = new SpendingTransactionResponse();
            spending.setName(tran.has("description") && !tran.get("description").isNull() ? tran.get("description").asText() : "");
            spending.setDate(LocalDate.parse(tran.get("timestamp").asText().substring(0, 10)));
            
            String currencyCode = tran.has("currency") ? tran.get("currency").asText() : "GBP";
            Double amount = tran.get("amount").asDouble() * -1;
            MonetaryAmount originalAmount = Money.of(amount, currencyCode);
            MonetaryAmount amountInEur = euroConversion.apply(originalAmount);

            spending.setAmount( amountInEur.getNumber()
                .numberValue(BigDecimal.class)
                .setScale(2, RoundingMode.HALF_UP)
            );
            String establishmentName = (tran.has("merchant_name") && !tran.get("merchant_name").isNull()) ? tran.get("merchant_name").asText() : "";
            if(establishmentName != null && !establishmentName.isEmpty()) {
                Establishment est = establishmentService.findByName(establishmentName);
                spending.setEstablishment(est != null ? est : new Establishment(0, establishmentName));
            } else {
                // The description usually contains the establishment name when merchant_name is not present, so we use it as a fallback
                Establishment est = establishmentService.findByName(spending.getName());
                if(est != null) {
                    spending.setEstablishment(est);
                } else {
                    spending.setEstablishment(new Establishment(0, spending.getName()));
                }
            }
            if(spending.getEstablishment().getId() != 0){// If the esstablishment already exists, we will look for if an older spending has been associated to it and to an alive category
                Category cat = spendingService.getCategoryByEstablishment(spending.getEstablishment().getId());
                spending.setIdCategory(cat != null ? cat.getId() : null);
            }
            transactionPairs.add(new Object[]{new TransactionExtractedResponse(transactionId, account), spending});
        }

        response.setTransactions(transactionPairs);
        return ResponseEntity.ok(response);
    }

}
