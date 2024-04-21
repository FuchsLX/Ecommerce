package com.springboot.ecommerce.security.registration.emailRegistration;

public interface EmailService {
    void sendSimpleMail(EmailDetails details);

    String buildVerifyEmail(String to, String link);
}
