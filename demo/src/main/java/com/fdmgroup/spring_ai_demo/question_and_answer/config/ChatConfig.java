package com.fdmgroup.spring_ai_demo.question_and_answer.config;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatClient chatClient(
            ChatClient.Builder builder,
            VectorStore vectorStore) {

        return builder
                .defaultAdvisors(
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .build())
                .build();
    }
}