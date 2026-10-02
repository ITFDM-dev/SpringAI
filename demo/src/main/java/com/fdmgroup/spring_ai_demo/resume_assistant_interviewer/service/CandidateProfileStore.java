package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto.CandidateProfile;

@Service
public class CandidateProfileStore {

    private final Map<String, CandidateProfile> profiles =
            new ConcurrentHashMap<>();

    public void save(
            CandidateProfile profile) {

        profiles.put(
                profile.getResumeId(),
                profile);
    }

    public CandidateProfile findByResumeId(
            String resumeId) {

        return profiles.get(resumeId);
    }
}