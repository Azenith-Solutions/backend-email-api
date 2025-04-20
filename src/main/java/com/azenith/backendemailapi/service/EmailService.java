package com.azenith.backendemailapi.service;

import com.azenith.backendemailapi.model.EmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class EmailService {

    @Value("${BREVO.API.KEY}")
    private String apiKey;

    @Value("${BREVO.API.URL}")
    private String apiUrl;

    private final WebClient webClient;

    public EmailService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<String> sendEmail(EmailRequest request) {
        Map<String, Object> payload = Map.of(
                "sender", Map.of("name", "Seu App", "email", "email@dominio.com"), // Alterar para email HardwareTech
                "to", new Map[]{ Map.of("email", request.toEmail, "name", request.toName) },
                "subject", request.subject,
                "htmlContent", "<html><body>" + request.content + "</body></html>"
        );

        return webClient.post()
                .uri(apiUrl)
                .header("api-key", apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class);
    }
}
