package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto;


public record ResumeUploadResponse(
    String resumeId,
    String fileName,
    int chunksStored,
    String status){
        
}
