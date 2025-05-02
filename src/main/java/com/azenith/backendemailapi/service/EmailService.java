package com.azenith.backendemailapi.service;

import com.azenith.backendemailapi.model.EmailRequest;
import com.azenith.backendemailapi.observer.EmailNotication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class EmailService {

    @Value("${api.brevo.key}")
    private String apiKey;

    @Value("${api.brevo.url}")
    private String apiUrl;

    private final WebClient webClient;
    private final EmailNotication emailNotication;

    public EmailService(WebClient.Builder webClientBuilder, EmailNotication emailNotication) {
        this.webClient = webClientBuilder.build();
        this.emailNotication = new EmailNotication();
    }

    public Mono<String> sendEmail(EmailRequest request) {
        Map<String, Object> payload = Map.of(
                "sender", Map.of("name", "Seu App", "email", "azenithsolutions@gmail.com"), // Alterar para email HardwareTech
                "to", new Map[]{Map.of("email", request.getToEmail(), "name", request.getToName())},
                "subject", request.getSubject(),
                "htmlContent", "<html><body>" + request.getContent() + "</body></html>"
        );

        return webClient.post()
                .uri(apiUrl)
                .header("api-key", apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(payload)
                .retrieve() // Inicia recuperação da resposta
                .bodyToMono(String.class)
                .onErrorResume(e -> {
                    System.err.println("Error sending email: " + e.getMessage());
                    return Mono.error(new RuntimeException("Failed to send email"));
                });
    }
}
