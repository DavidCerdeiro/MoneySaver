package com.TFG.app.backend.account.controller;

import com.TFG.app.backend.account.dto.AccountResponse;
import com.TFG.app.backend.account.dto.AllUserAccountsResponse;
import com.TFG.app.backend.account.dto.ExtractAccountsRequest;
import com.TFG.app.backend.account.entity.Account;
import com.TFG.app.backend.account.service.AccountService;
import com.TFG.app.backend.infraestructure.config.AccessTokenTrueLayer;
import com.TFG.app.backend.infraestructure.config.JwtService;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final UserService userService;
    private final WebClient webClient;

    @Value("${truelayer.client-id}")
    private String truelayerClientId;

    @Value("${truelayer.client-secret}")
    private String truelayerSecret;

    @Value("${truelayer.redirect-url}")
    private String truelayerRedirectUrl;

    public AccountController(AccountService accountService, JwtService jwtService, UserService userService) {
        this.accountService = accountService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.webClient = WebClient.builder().build();
    }

    @PostMapping("/extract")
    public ResponseEntity<List<AccountResponse>> extract(@CookieValue(name = "accessToken", required = false) String token, @RequestBody ExtractAccountsRequest request) throws JsonMappingException, JsonProcessingException {
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
        // Con el token llamar a /accounts
        String accountsUrl = "https://api.truelayer-sandbox.com/data/v1/accounts";
        String body = webClient.get()
                .uri(accountsUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        List<AccountResponse> accountResponses = new ArrayList<>();
        System.out.println(body);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode accountsJson = mapper.readTree(body);
        for (JsonNode acc : accountsJson.get("results")) {
            Account account = new Account();
            account.setUser(user);
            account.setBankName(acc.get("provider").get("display_name").asText());
            account.setNumber(acc.get("account_number").get("number").asText());
            account.setName(acc.get("display_name").asText());
            account.setTrueLayerId(acc.get("account_id").asText());
            if(!accountService.existByUserAndTrueLayerId(user, account.getTrueLayerId())) {
                accountService.createAccount(account);
                accountResponses.add(new AccountResponse(account.getId(), account.getTrueLayerId(), account.getName(), account.getNumber(), account.getBankName()));
            }   
        }

        return ResponseEntity.ok(accountResponses);
    }

    @GetMapping("/all")
    public ResponseEntity<AllUserAccountsResponse> allUserAccounts(@CookieValue(name = "accessToken", required = false) String token) {
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
        return new ResponseEntity<>(new AllUserAccountsResponse(accounts), HttpStatus.OK);
    }
    
    
    

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@CookieValue(name = "accessToken", required = false) String token, @RequestParam("id") Long id) {
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
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
