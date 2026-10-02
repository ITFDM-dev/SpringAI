package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service
public class QuestionWriterAgent {

    private final ChatClient chatClient;

    public QuestionWriterAgent(
            ChatClient.Builder builder) {

        this.chatClient = builder.build();
    }

    public String generateQuestions(
            String role,
            String plan,
            String resumeContext) {

        String prompt = """
                You are an interview question writer.

                Role:
                %s

                Resume:
                %s

                Focus Areas:
                %s

                Generate 15 interview questions.
                """
                .formatted(
                        role,
                        resumeContext,
                        plan);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}

