package com.fdmgroup.spring_ai_mcp_email_server.tool;

import com.fdmgroup.spring_ai_mcp_email_server.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.mcp.annotation.McpTool;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailMcpToolsTest {

    @Mock
    private EmailService emailService;

    private EmailMcpTools emailMcpTools;

    @BeforeEach
    void setUp() {
        emailMcpTools = new EmailMcpTools(emailService);
    }

    @Test
    void toolDelegatesToEmailServiceAndReturnsSuccessMessage() {
        String result = emailMcpTools.sendEmail("consultant@example.com", "Interview subject", "Interview body");

        assertThat(result).isEqualTo("Email sent successfully to consultant@example.com");
        verify(emailService).send("consultant@example.com", "Interview subject", "Interview body");
    }

    @Test
    void emailServiceFailurePreventsSuccessResponse() {
        doThrow(new IllegalStateException("SMTP failed")).when(emailService)
                .send("consultant@example.com", "Interview subject", "Interview body");

        assertThatThrownBy(() -> emailMcpTools.sendEmail("consultant@example.com", "Interview subject", "Interview body"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("SMTP failed");
    }

    @Test
    void toolNameAndRegistrationAreDiscoverable() throws NoSuchMethodException {
        Method method = EmailMcpTools.class.getDeclaredMethod("sendEmail", String.class, String.class, String.class);

        assertThat(method).isNotNull();
        assertThat(method.getAnnotation(McpTool.class)).isNotNull();
        assertThat(method.getParameters()).hasSize(3);
    }
}
