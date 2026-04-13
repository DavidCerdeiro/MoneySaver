package com.TFG.app.backend.infraestructure.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY:NOT_FOUND}")
    private String apiKey;

    @Value("${BREVO_SENDER_EMAIL:NOT_FOUND}")
    private String senderEmail;

    // Jackson ObjectMapper para crear JSON seguro
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Async
    public void sendEmail(String to, String subject, String content) {
        try {
            if ("NOT_FOUND".equals(apiKey) || "NOT_FOUND".equals(senderEmail)) {
                System.err.println("ERROR: No se han configurado las variables de Brevo.");
                return;
            }

            // Convertimos los \n de tu archivo properties a etiquetas <br> para que se vea bien en el correo
            String htmlContent = content.replace("\n", "<br>");

            // 1. Construimos la estructura de datos usando Mapas (seguro y limpio)
            Map<String, Object> payloadMap = new HashMap<>();
            
            // Remitente
            Map<String, String> sender = new HashMap<>();
            sender.put("name", "MoneySaver");
            sender.put("email", senderEmail);
            payloadMap.put("sender", sender);
            
            // Destinatario
            Map<String, String> recipient = new HashMap<>();
            recipient.put("email", to);
            payloadMap.put("to", List.of(recipient));
            
            // Asunto y Contenido
            payloadMap.put("subject", subject);
            payloadMap.put("htmlContent", htmlContent);

            // 2. Jackson convierte el Mapa a un JSON 100% válido y escapado automáticamente
            String jsonPayload = objectMapper.writeValueAsString(payloadMap);

            // 3. Enviamos la petición
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("api-key", apiKey)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
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