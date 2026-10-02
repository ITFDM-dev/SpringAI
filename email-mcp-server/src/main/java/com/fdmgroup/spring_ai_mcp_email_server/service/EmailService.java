package com.fdmgroup.spring_ai_mcp_email_server.service;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromAddress;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${mail.from}") String fromAddress) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
    }

    public void send(String to, String subject, String body) {
        validateRecipient(to);
        validateSubject(subject);
        validateBody(body);

        String normalizedTo = to.trim();

        try {
            new InternetAddress(normalizedTo, true);
        } catch (AddressException ex) {
            throw new IllegalArgumentException("Invalid recipient email address: " + normalizedTo, ex);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(normalizedTo);
        message.setSubject(subject.trim());
        message.setText(body.trim());

        try {
            mailSender.send(message);
        } catch (MailException ex) {
            throw new IllegalStateException(
                    "Failed to send email to " + normalizedTo + ". SMTP configuration or connectivity may be unavailable.",
                    ex);
        }
    }

    private void validateRecipient(String to) {
        if (!StringUtils.hasText(to)) {
            throw new IllegalArgumentException("Recipient email address is required.");
        }
    }

    private void validateSubject(String subject) {
        if (!StringUtils.hasText(subject)) {
            throw new IllegalArgumentException("Email subject is required.");
        }
    }

    private void validateBody(String body) {
        if (!StringUtils.hasText(body)) {
            throw new IllegalArgumentException("Email body is required.");
        }
    }
}
