package com.azenith.backendemailapi.observer;

import com.azenith.backendemailapi.model.EmailRequest;
import org.springframework.stereotype.Component;

@Component
public class EmailNotication implements Notificar{
    @Override
    public void notificar(EmailRequest emailRequest) {
        System.out.println("Email com sucesso para: " + emailRequest.getToEmail());
    }
}
