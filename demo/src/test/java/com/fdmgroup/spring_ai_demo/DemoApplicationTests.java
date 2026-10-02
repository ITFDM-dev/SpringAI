package com.fdmgroup.spring_ai_demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.ai.mcp.client.enabled=false")
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
