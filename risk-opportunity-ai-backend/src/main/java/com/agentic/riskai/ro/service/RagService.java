package com.agentic.riskai.ro.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Slf4j
@Service
public class RagService {
    
    private final GeminiService geminiService;
    private final List<DocumentEmbedding> documentEmbeddings = Collections.synchronizedList(new ArrayList<>());

    @Value("${app.ai.use-mock:true}")
    private boolean useMockEmbeddings;
    
    @Data
    @AllArgsConstructor
    private static class DocumentEmbedding {
        private String content;
        private String contentType;
        private List<Double> embedding;
        private Map<String, Object> metadata;
        private LocalDateTime createdAt;
    }
    
    public RagService(GeminiService geminiService) {
        this.geminiService = geminiService;
        loadMockDocuments();
    }
    
    private void loadMockDocuments() {
        String[] sampleDocs = {
            "Risk Management: Identify, assess, and control threats to organization's capital and earnings.",
            "Opportunity Management: Recognize and exploit positive uncertainties that can benefit projects.",
            "Mitigation: Reduce probability or impact of negative risks through preventive actions.",
            "Exploitation: Enhance positive risks by increasing probability or maximizing benefits."
        };
        
        for (String doc : sampleDocs) {
            storeDocument(doc, "risk_management", Map.of("source", "mock_data"));
        }
        log.info("Loaded {} mock documents for RAG", sampleDocs.length);
    }
    
    public void storeDocument(String content, String contentType, Map<String, Object> metadata) {
        try {
            List<Double> embedding = generateMockEmbedding(content);
            
            DocumentEmbedding doc = new DocumentEmbedding(
                content, contentType, embedding, metadata, LocalDateTime.now()
            );
            documentEmbeddings.add(doc);
            log.info("Stored document with {} characters", content.length());
        } catch (Exception e) {
            log.warn("Using mock embeddings due to error: {}", e.getMessage());
            // Store with mock embeddings anyway
            DocumentEmbedding doc = new DocumentEmbedding(
                content, contentType, generateMockEmbedding(content), metadata, LocalDateTime.now()
            );
            documentEmbeddings.add(doc);
        }
    }
    
    private List<Double> generateMockEmbedding(String content) {
        // Generate deterministic mock embeddings based on content hash
        Random random = new Random(content.hashCode());
        List<Double> embedding = new ArrayList<>();
        for (int i = 0; i < 1536; i++) {
            embedding.add(random.nextDouble() * 2 - 1); // Values between -1 and 1
        }
        return embedding;
    }
    
    public List<String> findRelevantDocuments(String query, int limit) {
        if (documentEmbeddings.isEmpty()) {
            return Collections.emptyList();
        }
        
        try {
            List<Double> queryEmbedding = generateMockEmbedding(query);
            
            // Calculate cosine similarity for each document
            List<DocumentSimilarity> similarities = documentEmbeddings.stream()
                .map(doc -> new DocumentSimilarity(doc, cosineSimilarity(queryEmbedding, doc.getEmbedding())))
                .sorted((d1, d2) -> Double.compare(d2.similarity, d1.similarity)) // descending
                .limit(limit)
                .collect(Collectors.toList());
            
            return similarities.stream()
                .map(ds -> ds.document.getContent())
                .collect(Collectors.toList());
            
        } catch (Exception e) {
            log.error("Error finding relevant documents", e);
            return Collections.emptyList();
        }
    }
    
    private double cosineSimilarity(List<Double> vectorA, List<Double> vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        
        for (int i = 0; i < vectorA.size(); i++) {
            dotProduct += vectorA.get(i) * vectorB.get(i);
            normA += Math.pow(vectorA.get(i), 2);
            normB += Math.pow(vectorB.get(i), 2);
        }
        
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
    
    private record DocumentSimilarity(DocumentEmbedding document, double similarity) {}
    
    public String analyzeWithRAG(String userQuery) {
        List<String> relevantDocs = findRelevantDocuments(userQuery, 3);    
        String context = relevantDocs.isEmpty() ? "No relevant documents found." : 
            String.join("\n\n", relevantDocs);
        
        String prompt = String.format("""
            Based on the following context about risks and opportunities analysis:
            
            CONTEXT:
            %s
            
            USER QUERY: %s
            
            Please provide a comprehensive analysis considering:
            1. Risk/Opportunity identification
            2. Impact assessment
            3. Probability evaluation
            4. Mitigation/Exploitation strategies
            5. Priority classification
            
            Format the response in a structured way with clear sections.
            """, context, userQuery);
        
        return geminiService.generateContent(prompt);
    }
    
    public int getDocumentCount() {
        return documentEmbeddings.size();
    }
}