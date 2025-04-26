package com.azenith.backendemailapi.controller;

import com.azenith.backendemailapi.model.EmailRequest;
import com.azenith.backendemailapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public Mono<ResponseEntity<String>> sendEmail(@RequestBody EmailRequest request) {
        return emailService.sendEmail(request)
                .map(response -> ResponseEntity.ok("Enviado com sucesso: " + response))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Erro: " + e.getMessage())));
    }
}