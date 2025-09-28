package com.agentic.riskai.ro.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.agentic.riskai.ro.model.RiskAnalysisResult;
import com.agentic.riskai.ro.model.AnalysisType;
import com.agentic.riskai.ro.model.RiskAnalysisResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RiskOpportunityAIService {
    
    private final GeminiService geminiService;
    private final RagService ragService;
    private final MockAIService mockAIService;

    @Value("${app.ai.use-mock:true}")
    private boolean useMockAI;
    
    private final String ANALYSIS_PROMPT = """
        Analyze the following risk or opportunity description and provide a structured analysis.
        
        Description: %s
        Type: %s
        
        Please analyze and return a JSON response in this exact format:
        {
            "category": "string (Technical/Business/Operational/Financial/Market)",
            "impactScore": number (1-10),
            "probability": number (0-1),
            "priority": "LOW/MEDIUM/HIGH/CRITICAL",
            "detailedAnalysis": "string",
            "mitigationStrategy": "string (for risks)",
            "exploitationPlan": "string (for opportunities)",
            "keyFactors": ["factor1", "factor2", "factor3"]
        }
        """;
    
    public RiskOpportunityAIService(GeminiService geminiService, 
                                   RagService ragService,
                                   MockAIService mockAIService) {
        this.geminiService = geminiService;
        this.ragService = ragService;
        this.mockAIService = mockAIService;
    }
    
    public RiskAnalysisResult analyzeRiskOpportunity(String description, AnalysisType type) {
        if (useMockAI) {
            return mockAIService.analyzeRiskOpportunity(description, type);
        }
        
        try {            
            String prompt = String.format(ANALYSIS_PROMPT, description, type);
            String aiResponse = geminiService.generateContent(prompt);
            log.info("Gemini response -------------------------- : {}", aiResponse);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(aiResponse, RiskAnalysisResult.class);
            
        } catch (Exception e) {
            // Fallback to mock service if Gemini fails
            return mockAIService.analyzeRiskOpportunity(description, type);
        }
    }
    
    public String getAIAnalysisWithRAG(String query) {
        log.info("RAG query -------------------------- : {}", query);
        log.info("Using mock AI -------------------------- : {}  ", useMockAI);
        if (useMockAI) {
            return mockAIService.analyzeWithRAG(query);
        }
        
        try {
            String response = ragService.analyzeWithRAG(query);
            log.info("RAG response -------------------------- : {}", response);
            return response;
        } catch (Exception e) {
            // Fallback to mock service if Gemini fails
            return mockAIService.analyzeWithRAG(query);
        }
    }
}