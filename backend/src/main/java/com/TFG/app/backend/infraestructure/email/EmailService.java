package com.TFG.app.backend.infraestructure.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Service to handle email sending functionality via Brevo HTTP API.
 */
@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    /**
     * Method to send an email asynchronously.
     * It takes the recipient's email address, subject, and content as parameters.
     */
    @Async
    public void sendEmail(String to, String subject, String content) {
        try {
            String safeContent = content.replace("\"", "\\\"");
            
            String payload = String.format(
                "{" +
                "\"sender\": {\"name\": \"Mi Portfolio TFG\", \"email\": \"%s\"}," +
                "\"to\": [{\"email\": \"%s\"}]," +
                "\"subject\": \"%s\"," +
                "\"htmlContent\": \"%s\"" +
                "}",
                senderEmail, to, subject, safeContent
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("api-key", apiKey)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() >= 400) {
                System.err.println("Fallo al enviar correo mediante Brevo: " + response.statusCode() + " - " + response.body());
            } else {
                System.out.println("Correo enviado con éxito a: " + to);
            }

        } catch (Exception e) {
            System.err.println("Excepción enviando email asíncrono: " + e.getMessage());
        }
    }
}
