package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InterviewEmailService {

    private static final Logger logger = LoggerFactory.getLogger(InterviewEmailService.class);
    private static final String SEND_EMAIL_TOOL = "sendEmail";

    private final ObjectProvider<ToolCallbackProvider> toolCallbackProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public InterviewEmailService(ObjectProvider<ToolCallbackProvider> toolCallbackProvider) {
        this.toolCallbackProvider = toolCallbackProvider;
    }

    public void sendEmail(
            String email,
            String role,
            String questions) {
        try {
            ToolCallback callback = findSendEmailCallback();
            String arguments = objectMapper.writeValueAsString(Map.of(
                    "to", email,
                    "subject", "Interview Questions - " + role,
                    "body", questions));

            callback.call(arguments);
            logger.info("Interview questions sent to {} through MCP", email);
        } catch (JsonProcessingException exception) {
            logger.error("Could not create arguments for MCP sendEmail", exception);
            throw emailServiceUnavailable();
        } catch (Exception exception) {
            logger.error("MCP sendEmail invocation failed for {}", email, exception);
            throw emailServiceUnavailable();
        }
    }

    private ToolCallback findSendEmailCallback() {
        ToolCallbackProvider provider = toolCallbackProvider.getIfAvailable();
        if (provider == null) {
            throw new IllegalStateException("MCP tool callbacks are unavailable");
        }

        return Arrays.stream(provider.getToolCallbacks())
                .filter(callback -> SEND_EMAIL_TOOL.equals(
                        callback.getToolDefinition().name()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "MCP sendEmail tool was not discovered"));
    }

    private ResponseStatusException emailServiceUnavailable() {
        return new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Email service is unavailable or failed to send the email.");
    }
}