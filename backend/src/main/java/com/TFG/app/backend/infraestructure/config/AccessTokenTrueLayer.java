package com.TFG.app.backend.infraestructure.config;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

public class AccessTokenTrueLayer {
    private final WebClient webClient;

    public AccessTokenTrueLayer() {
        this.webClient = WebClient.builder().build();
    }

    public String getAccessToken(String clientId, String clientSecret, String redirectUri, String code) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);

        // Logic to obtain access token from TrueLayer API
        JsonNode tokenResponse = webClient.post()
                .uri("https://auth.truelayer-sandbox.com/connect/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (tokenResponse != null) {
            return tokenResponse.get("access_token").asText();
        }

        return null;
    }
}
