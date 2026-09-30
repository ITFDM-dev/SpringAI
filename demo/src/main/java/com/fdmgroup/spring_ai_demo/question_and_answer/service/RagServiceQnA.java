package com.fdmgroup.spring_ai_demo.question_and_answer.service;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;

@Service
public class RagServiceQnA {

    private final ChatClient chatClient;

    public RagServiceQnA(
            ChatClient chatClient) {

        this.chatClient = chatClient;
    }

    public String ask(String question) {

        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
