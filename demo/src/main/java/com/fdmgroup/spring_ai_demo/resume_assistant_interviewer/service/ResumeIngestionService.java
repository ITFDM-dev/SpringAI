package com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.service;

import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.FileSystemResource;
import com.fdmgroup.spring_ai_demo.resume_assistant_interviewer.dto.ResumeUploadResponse;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;

@Service
public class ResumeIngestionService {

    private final VectorStore vectorStore;

    public ResumeIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public ResumeUploadResponse upload(MultipartFile file) throws IOException {
        String resumeId = UUID.randomUUID().toString();
        Path tempFile = Files.createTempFile("resume", ".pdf");
        file.transferTo(tempFile);

        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(new FileSystemResource(tempFile));

        List<Document> documents = pdfReader.read();

        TokenTextSplitter splitter = TokenTextSplitter.builder().build();

        List<Document> chunks = splitter.split(documents);

        chunks.forEach(chunk -> {
            chunk.getMetadata().put("resumeId", resumeId);
            chunk.getMetadata().put("fileName", file.getOriginalFilename());
            chunk.getMetadata().put("source", "resume");
            System.out.println("Added resumeId metadata to chunk: " + chunk.getMetadata().get("resumeId"));
        });

        vectorStore.add(chunks);

        System.out.println("========== RESUME CHUNKS ==========");
        chunks.forEach(chunk -> {
            System.out.println(chunk.getText());
            System.out.println("--------------------------------");
        });

        return new ResumeUploadResponse(resumeId,
                file.getOriginalFilename(),
                chunks.size(),
                "INDEXED"
        );
    }
}

