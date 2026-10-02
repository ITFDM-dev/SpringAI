package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto;

import java.util.List;

public class CandidateSearchResponse {

    private String query;
    private int candidatesFound;
    private List<CandidateMatch> candidates;

    public CandidateSearchResponse() {
    }

    public CandidateSearchResponse(
            String query,
            int candidatesFound,
            List<CandidateMatch> candidates) {

        this.query = query;
        this.candidatesFound = candidatesFound;
        this.candidates = candidates;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getCandidatesFound() {
        return candidatesFound;
    }

    public void setCandidatesFound(int candidatesFound) {
        this.candidatesFound = candidatesFound;
    }

    public List<CandidateMatch> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateMatch> candidates) {
        this.candidates = candidates;
    }
}