package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class QuestionReviewerAgent {

    private final ChatClient chatClient;

    public QuestionReviewerAgent(
            ChatClient.Builder builder) {

        this.chatClient = builder.build();
    }

    public String reviewQuestions(
            String draftQuestions) {

        String prompt = """
                Review these interview questions.

                Remove duplicates.

                Improve clarity.

                Order from easier to harder.

                Return the final 10 questions.

                %s
                """
                .formatted(draftQuestions);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
