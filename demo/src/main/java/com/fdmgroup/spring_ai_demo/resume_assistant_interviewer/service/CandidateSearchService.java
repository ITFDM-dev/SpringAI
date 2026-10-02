package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto.CandidateMatch;
import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto.CandidateSearchResponse;

@Service
public class CandidateSearchService {

    private final VectorStore vectorStore;

    public CandidateSearchService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public CandidateSearchResponse searchCandidate(String query) {

        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException(
                    "Search query must not be empty");
        }

        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(10)
                .filterExpression("source == 'resume'")
                .build();

        List<Document> results =
                vectorStore.similaritySearch(request);

        Map<String, CandidateAccumulator> groupedCandidates =
                new LinkedHashMap<>();

        for (Document document : results) {

            Object resumeIdValue =
                    document.getMetadata().get("resumeId");

            if (resumeIdValue == null) {
                continue;
            }

            String resumeId = resumeIdValue.toString();

            Object fileNameValue =
                    document.getMetadata().get("fileName");

            String fileName = fileNameValue == null
                    ? "Unknown resume"
                    : fileNameValue.toString();

            CandidateAccumulator candidate =
                    groupedCandidates.computeIfAbsent(
                            resumeId,
                            id -> new CandidateAccumulator(
                                    id,
                                    fileName));

            candidate.addEvidence(document.getText());
        }

        List<CandidateMatch> matches =
                groupedCandidates.values()
                        .stream()
                        .map(CandidateAccumulator::toCandidateMatch)
                        .toList();

        return new CandidateSearchResponse(
                query,
                matches.size(),
                matches);
    }

    public String getResumeContext(String resumeId, String role) {

        SearchRequest request = SearchRequest.builder()
                .query(role)
                .topK(10)
                .filterExpression(
                        "resumeId == '" +
                                resumeId +
                                "' AND source == 'resume'")
                .build();

        List<Document> results =
                vectorStore.similaritySearch(request);

        StringBuilder context =
                new StringBuilder();

        results.forEach(document ->
                context.append(document.getText())
                        .append("\n\n"));

        return context.toString();
    }

    private static class CandidateAccumulator {

        private final String resumeId;
        private final String fileName;
        private final List<String> evidence =
                new ArrayList<>();

        private CandidateAccumulator(
                String resumeId,
                String fileName) {

            this.resumeId = resumeId;
            this.fileName = fileName;
        }

        private void addEvidence(String text) {

            if (text == null || text.isBlank()) {
                return;
            }

            if (!evidence.contains(text)) {
                evidence.add(text);
            }
        }

        private CandidateMatch toCandidateMatch() {

            return new CandidateMatch(
                    resumeId,
                    fileName,
                    evidence.stream()
                            .limit(3)
                            .toList());
        }
    }
}