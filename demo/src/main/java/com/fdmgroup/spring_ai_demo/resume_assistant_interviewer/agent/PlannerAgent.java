package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class PlannerAgent {

    private final ChatClient chatClient;

    public PlannerAgent(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String createPlan(
            String role,
            String resumeContext) {

        String prompt = """
                You are an interview planning specialist.

                Role:
                %s

                Candidate Resume:
                %s

                Identify the major interview focus areas.

                Return 5-8 focus areas only.
                """
                .formatted(role, resumeContext);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
