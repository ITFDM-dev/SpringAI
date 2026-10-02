package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto;

public class CandidateProfile {

    private String resumeId;
    private String candidateName;
    private String email;
    private String phone;

    public CandidateProfile() {
    }

    public CandidateProfile(
            String resumeId,
            String candidateName,
            String email,
            String phone) {

        this.resumeId = resumeId;
        this.candidateName = candidateName;
        this.email = email;
        this.phone = phone;
    }

    public String getResumeId() {
        return resumeId;
    }

    public void setResumeId(String resumeId) {
        this.resumeId = resumeId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(
            String candidateName) {
        this.candidateName = candidateName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}