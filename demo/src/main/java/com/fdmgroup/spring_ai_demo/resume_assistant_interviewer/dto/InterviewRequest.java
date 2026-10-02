package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto;

public class InterviewRequest {

    private String resumeId;
    private String role;

    public String getResumeId() {
        return resumeId;
    }

    public void setResumeId(String resumeId) {
        this.resumeId = resumeId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}