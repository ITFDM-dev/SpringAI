package com.fdmgroup.spring_ai_mcp_email_server.tool;

import com.fdmgroup.spring_ai_mcp_email_server.service.EmailService;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EmailMcpTools {

    private final EmailService emailService;

    public EmailMcpTools(EmailService emailService) {
        this.emailService = emailService;
    }

    @McpTool(name = "sendEmail", description = "Send an email containing recruiter-approved interview questions to a consultant.")
    public String sendEmail(
            @McpToolParam(description = "Recipient email address.") String to,
            @McpToolParam(description = "Email subject.") String subject,
            @McpToolParam(description = "Email body containing the approved interview questions.") String body) {

        if (!StringUtils.hasText(to)) {
            throw new IllegalArgumentException("Recipient email address is required.");
        }
        if (!StringUtils.hasText(subject)) {
            throw new IllegalArgumentException("Email subject is required.");
        }
        if (!StringUtils.hasText(body)) {
            throw new IllegalArgumentException("Email body is required.");
        }

        emailService.send(to, subject, body);
        return "Email sent successfully to " + to.trim();
    }
}
