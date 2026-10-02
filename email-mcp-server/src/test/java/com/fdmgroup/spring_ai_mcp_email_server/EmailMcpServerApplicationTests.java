package com.fdmgroup.spring_ai_mcp_email_server;

import com.fdmgroup.spring_ai_mcp_email_server.service.EmailService;
import com.fdmgroup.spring_ai_mcp_email_server.tool.EmailMcpTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "EMAIL_USERNAME=test@example.com",
        "EMAIL_APP_PASSWORD=test-app-password"
})
class EmailMcpServerApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
        assertThat(applicationContext.containsBean("emailService")).isTrue();
        assertThat(applicationContext.containsBean("emailMcpTools")).isTrue();
    }

    @Test
    void requiredBeansArePresent() {
        assertThat(applicationContext.getBean(EmailService.class)).isNotNull();
        assertThat(applicationContext.getBean(EmailMcpTools.class)).isNotNull();
    }
}
