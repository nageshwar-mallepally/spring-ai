package com.agentic.riskai.ro.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import com.agentic.riskai.ro.model.AnalysisType;
import com.agentic.riskai.ro.model.RiskAnalysisResult;
import org.springframework.beans.factory.annotation.Value;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class MockAIService {
    
    private final Random random = new Random();
    private final List<String> categories = Arrays.asList(
        "Technical", "Business", "Operational", "Financial", "Market", "Strategic"
    );
    
    private final List<String> priorities = Arrays.asList("LOW", "MEDIUM", "HIGH", "CRITICAL");
    
    public RiskAnalysisResult analyzeRiskOpportunity(String description, AnalysisType type) {
        // Simulate AI analysis with mock data
        return new RiskAnalysisResult(
            categories.get(random.nextInt(categories.size())),
            (double) random.nextInt(10) + 1, // 1-10
            random.nextDouble() * 0.9 + 0.1, // 0.1-1.0
            priorities.get(random.nextInt(priorities.size())),
            generateMockAnalysis(description, type),
            type == AnalysisType.RISK ? generateMitigationStrategy() : "",
            type == AnalysisType.OPPORTUNITY ? generateExploitationPlan() : "",
            Arrays.asList("Factor 1", "Factor 2", "Factor 3")
        );
    }
    
    public String analyzeWithRAG(String query) {
        return String.format("""
            Mock RAG Analysis for: %s
            
            Based on our risk management knowledge base:
            
            IDENTIFICATION: This appears to be a %s scenario.
            
            ASSESSMENT:
            - Impact Level: %s
            - Probability: %s
            - Priority: %s
            
            RECOMMENDATIONS:
            1. Conduct further analysis
            2. Develop mitigation strategy
            3. Monitor regularly
            
            This is a mock response since Gemini API is not configured.
            """, query, 
            query.toLowerCase().contains("opportunity") ? "potential opportunity" : "potential risk",
            random.nextBoolean() ? "Medium" : "High",
            (random.nextDouble() * 80 + 20) + "%",
            priorities.get(random.nextInt(priorities.size()))
        );
    }
    
    private String generateMockAnalysis(String description, AnalysisType type) {
        return String.format("Mock AI analysis of this %s: '%s'. This is a simulated response.", 
            type.toString().toLowerCase(), description);
    }
    
    private String generateMitigationStrategy() {
        return "Mock mitigation strategy: Regular monitoring and contingency planning recommended.";
    }
    
    private String generateExploitationPlan() {
        return "Mock exploitation plan: Develop action plan to maximize benefits.";
    }
}