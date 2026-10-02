package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.controller;

import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto.ResumeUploadResponse;
import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto.CandidateSearchResponse;
import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.service.ResumeIngestionService;
import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.service.CandidateSearchService;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeIngestionService resumeIngestionService;
    private final CandidateSearchService candidateSearchService;

    public ResumeController(ResumeIngestionService resumeIngestionService,
            CandidateSearchService candidateSearchService) {
        this.resumeIngestionService = resumeIngestionService;
        this.candidateSearchService = candidateSearchService;
    }

    @PostMapping("/upload")
    public ResumeUploadResponse upload(@RequestParam("file") MultipartFile file) throws IOException {
        return resumeIngestionService.upload(file);
    }
}
