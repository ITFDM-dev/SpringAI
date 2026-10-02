package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.service;
import org.springframework.stereotype.Service;

import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.agent.PlannerAgent;
import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.agent.QuestionReviewerAgent;
import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.agent.QuestionWriterAgent;

@Service
public class InterviewWorkflowService {

    private final CandidateSearchService candidateSearchService;
    private final PlannerAgent plannerAgent;
    private final QuestionWriterAgent questionWriterAgent;
    private final QuestionReviewerAgent questionReviewerAgent;

    public InterviewWorkflowService(
            CandidateSearchService candidateSearchService,
            PlannerAgent plannerAgent,
            QuestionWriterAgent questionWriterAgent,
            QuestionReviewerAgent questionReviewerAgent) {

        this.candidateSearchService =
                candidateSearchService;

        this.plannerAgent =
                plannerAgent;

        this.questionWriterAgent =
                questionWriterAgent;

        this.questionReviewerAgent =
                questionReviewerAgent;
    }

    public String generateQuestions(
            String resumeId,
            String role) {

        String resumeContext =
                candidateSearchService
                    .getResumeContext(
                            resumeId,
                            role);

        String plan =
                plannerAgent.createPlan(
                        role,
                        resumeContext);

        String draftQuestions =
                questionWriterAgent.generateQuestions(
                        role,
                        plan,
                        resumeContext);

        return questionReviewerAgent
                .reviewQuestions(
                        draftQuestions);
    }
}