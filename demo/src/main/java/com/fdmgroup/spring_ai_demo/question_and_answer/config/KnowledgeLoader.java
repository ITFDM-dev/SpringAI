package com.fdmgroup.spring_ai_demo.question_and_answer.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import java.util.List;

@Configuration
public class KnowledgeLoader {

    @Bean
    CommandLineRunner loadContextKnowledge(
            VectorStore vectorStore,
            ResourceLoader resourceLoader) {

        return args -> {

            var resource =
                    resourceLoader.getResource(
                            "classpath:/context.txt");

            var reader =
                    new TextReader(resource);

            List<Document> documents =
                    reader.get();

            TokenTextSplitter  splitter =
                    TokenTextSplitter.builder()
                    .withChunkSize(500)
                    .withMinChunkSizeChars(100)
                    .withMinChunkLengthToEmbed(5)
                    .withMaxNumChunks(10000)
                    .withKeepSeparator(true)
                    .build();

            List<Document> chunks =
                    splitter.apply(documents);

            vectorStore.add(chunks);

            System.out.println(
                    "Knowledge Base Loaded");
        };
    }
}