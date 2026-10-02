package com.fdmgroup.spring_ai_mcp_email_server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    private EmailService emailService;

    @BeforeEach
    void setUp() {
        emailService = new EmailService(javaMailSender, "noreply@example.com");
    }

    @Test
    void validInputBuildsAndSendsAMailMessage() {
        emailService.send("consultant@example.com", "Interview questions", "Approved body");

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void blankRecipientIsRejected() {
        assertThatThrownBy(() -> emailService.send("   ", "subject", "body"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Recipient email address is required");
    }

    @Test
    void invalidRecipientIsRejected() {
        assertThatThrownBy(() -> emailService.send("invalid-email", "subject", "body"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid recipient email address");
    }

    @Test
    void blankSubjectIsRejected() {
        assertThatThrownBy(() -> emailService.send("consultant@example.com", "   ", "body"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email subject is required");
    }

    @Test
    void blankBodyIsRejected() {
        assertThatThrownBy(() -> emailService.send("consultant@example.com", "subject", "   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email body is required");
    }

    @Test
    void javaMailSenderFailureIsNotReportedAsSuccess() {
        MailException exception = new MailException("smtp failure") { };
        doThrow(exception).when(javaMailSender).send(any(SimpleMailMessage.class));

        assertThatThrownBy(() -> emailService.send("consultant@example.com", "subject", "body"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Failed to send email");
    }
}
