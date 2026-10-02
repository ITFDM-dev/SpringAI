package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto.CandidateSearchResponse;
import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.service.CandidateSearchService;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateSearchService candidateSearchService;

    public CandidateController(
            CandidateSearchService candidateSearchService) {

        this.candidateSearchService = candidateSearchService;
    }

    @GetMapping("/search")
    public CandidateSearchResponse search(
            @RequestParam String query) {

        return candidateSearchService.searchCandidate(query);
    }
}