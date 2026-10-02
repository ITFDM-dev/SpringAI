package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.controller;

import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto.EmailRequest;
import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto.InterviewRequest;
import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.service.InterviewEmailService;
import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.service.InterviewWorkflowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    private final InterviewWorkflowService interviewWorkflowService;
    private final InterviewEmailService interviewEmailService;

    public InterviewController(
            InterviewWorkflowService interviewWorkflowService,
            InterviewEmailService interviewEmailService) {

        this.interviewWorkflowService =
                interviewWorkflowService;
        this.interviewEmailService = interviewEmailService;       
    }

    @PostMapping("/generate")
    public String generateQuestions(
            @RequestBody InterviewRequest request) {

        return interviewWorkflowService
                .generateQuestions(
                        request.getResumeId(),
                        request.getRole());
    }

    @PostMapping("/send")
    public String sendQuestions(
        @RequestBody EmailRequest request) {

    String questions =
            interviewWorkflowService
                    .generateQuestions(
                        request.getResumeId(),
                        request.getRole());

    interviewEmailService.sendEmail(
            request.getEmail(),
            request.getRole(),
            questions);

    return "Questions sent";
}

}