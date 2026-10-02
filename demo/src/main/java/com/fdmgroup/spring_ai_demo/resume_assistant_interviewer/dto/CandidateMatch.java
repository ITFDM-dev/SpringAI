package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto;

import java.util.List;

public record CandidateMatch(
        String resumeId,
        String fileName,
        List<String> evidence) {
}
