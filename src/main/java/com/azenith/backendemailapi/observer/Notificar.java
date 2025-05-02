package com.azenith.backendemailapi.observer;


import com.azenith.backendemailapi.model.EmailRequest;

public interface Notificar {
    void notificar(EmailRequest emailRequest);
}
