package com.agentic.riskai.ro.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.agentic.riskai.ro.model.AnalysisRequest;
import com.agentic.riskai.ro.model.DocumentUploadRequest;
import com.agentic.riskai.ro.model.RiskAnalysisResult;
import com.agentic.riskai.ro.model.RiskOpportunityEntity;
import com.agentic.riskai.ro.service.RagService;
import com.agentic.riskai.ro.service.RiskOpportunityAIService;
import com.agentic.riskai.ro.service.RiskOpportunityService;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/risk-opportunity")
@CrossOrigin(origins = "http://localhost:3000")
public class RiskOpportunityController {
    
    @Autowired
    private RagService service;

    @Autowired
    private RiskOpportunityAIService aiService;

    @Autowired
    private RiskOpportunityService riskOpportunityService;
   
    
    @PostMapping("/analyze")
    public ResponseEntity<RiskOpportunityEntity> analyzeRiskOpportunity(
            @RequestBody AnalysisRequest request) {
        
        RiskAnalysisResult aiResult = aiService.analyzeRiskOpportunity(
            request.getDescription(), request.getType());
        
        RiskOpportunityEntity entity = riskOpportunityService.saveAnalysis(
            request.getTitle(),
            request.getDescription(),
            request.getType(),
            aiResult
        );
        
        return ResponseEntity.ok(entity);
    }
    
    @PostMapping("/rag-analysis")
    public ResponseEntity<String> analyzeWithRAG(@RequestBody String query) {
        String analysis = aiService.getAIAnalysisWithRAG(query);
        return ResponseEntity.ok(analysis);
    }
    
    @PostMapping("/documents")
    public ResponseEntity<Void> uploadDocument(@RequestBody DocumentUploadRequest request) {
        service.storeDocument(
            request.getContent(),
            request.getContentType(),
            request.getMetadata()
        );
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public ResponseEntity<List<RiskOpportunityEntity>> getAllAnalyses() {
        return ResponseEntity.ok(riskOpportunityService.findAll());
    }

    @GetMapping("/rag-status")
    public ResponseEntity<Map<String, Object>> getRagStatus() {
        try {
            Map<String, Object> status = new HashMap<>();
            status.put("documentCount", service.getDocumentCount());
            status.put("status", "active");
            status.put("useMockMode", true); // Add this line
            status.put("timestamp", LocalDateTime.now().toString());
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            Map<String, Object> errorStatus = new HashMap<>();
            errorStatus.put("documentCount", 0);
            errorStatus.put("status", "error");
            errorStatus.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorStatus);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RiskOpportunityEntity> getAnalysis(@PathVariable Long id) {
        return riskOpportunityService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
        if (riskOpportunityService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
