package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ResumeMetadataExtractor {

    public Map<String, Object> extract(String resumeText) {
        Map<String, Object> metadata = new HashMap<>();

        if (resumeText == null || resumeText.isBlank()) {
            metadata.put("resumeLength", 0);
            metadata.put("lineCount", 0);
            metadata.put("keywords", List.of());
            return metadata;
        }

        String normalized = resumeText.trim();
        String[] lines = normalized.split("\\r?\\n");

        metadata.put("resumeLength", normalized.length());
        metadata.put("lineCount", lines.length);
        metadata.put("keywords", extractKeywordHints(normalized));
        metadata.put("hasExperience", normalized.toLowerCase().contains("experience"));
        metadata.put("hasProjects", normalized.toLowerCase().contains("project"));
        return metadata;
    }

    private List<String> extractKeywordHints(String resumeText) {
        String lower = resumeText.toLowerCase();
        List<String> keywords = new ArrayList<>();

        String[] hints = {"java", "spring", "sql", "python", "aws", "azure", "leadership",
                "design", "architecture", "machine learning", "microservices", "api"};

        for (String hint : hints) {
            if (lower.contains(hint)) {
                keywords.add(hint);
            }
        }

        return keywords;
    }
}
