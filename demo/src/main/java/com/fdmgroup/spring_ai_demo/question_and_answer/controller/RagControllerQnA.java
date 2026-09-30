package com.fdmgroup.spring_ai_demo.question_and_answer.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fdmgroup.spring_ai_demo.question_and_answer.service.RagServiceQnA;

@RestController
@RequestMapping("/api/ragQnA")
public class RagControllerQnA {

    private final RagServiceQnA ragServiceQnA;

    public RagControllerQnA(
            RagServiceQnA ragServiceQnA) {

        this.ragServiceQnA = ragServiceQnA;
    }

    @GetMapping
    public String ask(
            @RequestParam String question) {

        return ragServiceQnA.ask(question);
    }
}