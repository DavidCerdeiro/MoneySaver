package com.TFG.app.backend.user.infraestructure;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}
