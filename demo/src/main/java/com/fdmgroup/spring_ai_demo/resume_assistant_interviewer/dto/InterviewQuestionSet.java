package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto;

import java.util.ArrayList;
import java.util.List;

public class InterviewQuestionSet {

    private String interviewId;
    private String candidateId;
    private String candidateName;
    private String positionTitle;
    private List<String> questions = new ArrayList<>();
    private String reviewNotes;

    public InterviewQuestionSet() {
    }

    public InterviewQuestionSet(String interviewId, String candidateId, String candidateName,
            String positionTitle, List<String> questions, String reviewNotes) {
        this.interviewId = interviewId;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.positionTitle = positionTitle;
        this.questions = questions;
        this.reviewNotes = reviewNotes;
    }

    public String getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public String getReviewNotes() {
        return reviewNotes;
    }

    public void setReviewNotes(String reviewNotes) {
        this.reviewNotes = reviewNotes;
    }
}
