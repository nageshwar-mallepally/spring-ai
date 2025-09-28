package com.agentic.riskai.ro.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.agentic.riskai.ro.model.RiskOpportunityEntity;
import com.agentic.riskai.ro.model.AnalysisType;
import com.agentic.riskai.ro.model.PriorityLevel;
import com.agentic.riskai.ro.repository.RiskOpportunityRepository;
import com.agentic.riskai.ro.model.RiskAnalysisResult;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RiskOpportunityService {
    
    @Autowired
    private RiskOpportunityRepository repository;
    
    public RiskOpportunityEntity saveAnalysis(String title, String description, 
                                            AnalysisType type, RiskAnalysisResult aiResult) {
        
        // Calculate risk score
        Double riskScore = aiResult.getImpactScore() * aiResult.getProbability();
        
        // Convert priority string to enum
        PriorityLevel priority = PriorityLevel.valueOf(aiResult.getPriority().toUpperCase());
        
        RiskOpportunityEntity entity = new RiskOpportunityEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setType(type);
        entity.setAiAnalysis(aiResult.getDetailedAnalysis());
        entity.setCategory(aiResult.getCategory());
        entity.setImpactScore(aiResult.getImpactScore());
        entity.setProbability(aiResult.getProbability());
        entity.setRiskScore(riskScore);
        entity.setPriority(priority);
        entity.setMitigationStrategy(aiResult.getMitigationStrategy());
        entity.setExploitationPlan(aiResult.getExploitationPlan());
        
        return repository.save(entity);
    }
    
    @Transactional(readOnly = true)
    public List<RiskOpportunityEntity> findAll() {
        return repository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<RiskOpportunityEntity> findById(Long id) {
        return repository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<RiskOpportunityEntity> findByType(AnalysisType type) {
        return repository.findByTypeOrderByCreatedAtDesc(type);
    }
    
    @Transactional(readOnly = true)
    public List<RiskOpportunityEntity> findByPriority(PriorityLevel priority) {
        return repository.findByPriorityOrderByRiskScoreDesc(priority);
    }
    
    @Transactional(readOnly = true)
    public List<RiskOpportunityEntity> findHighPriorityItems() {
        return repository.findByPriorityOrderByRiskScoreDesc(PriorityLevel.HIGH);
    }
    
    @Transactional(readOnly = true)
    public List<RiskOpportunityEntity> findCriticalItems() {
        return repository.findByPriorityOrderByRiskScoreDesc(PriorityLevel.CRITICAL);
    }
    
    public RiskOpportunityEntity updateMitigationStrategy(Long id, String mitigationStrategy) {
        RiskOpportunityEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risk/Opportunity not found with id: " + id));
        
        entity.setMitigationStrategy(mitigationStrategy);
        return repository.save(entity);
    }
    
    public RiskOpportunityEntity updateExploitationPlan(Long id, String exploitationPlan) {
        RiskOpportunityEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risk/Opportunity not found with id: " + id));
        
        entity.setExploitationPlan(exploitationPlan);
        return repository.save(entity);
    }
    
    public Boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Risk/Opportunity not found with id: " + id);            
        }
    }
    
    @Transactional(readOnly = true)
    public long getTotalCount() {
        return repository.count();
    }
    
    @Transactional(readOnly = true)
    public long getCountByType(AnalysisType type) {
        return repository.findByTypeOrderByCreatedAtDesc(type).size();
    }
    
    @Transactional(readOnly = true)
    public List<RiskOpportunityEntity> findTopRisks(int limit) {
        return repository.findAll().stream()
                .filter(entity -> entity.getType() == AnalysisType.RISK)
                .sorted((e1, e2) -> Double.compare(e2.getRiskScore(), e1.getRiskScore()))
                .limit(limit)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<RiskOpportunityEntity> findTopOpportunities(int limit) {
        return repository.findAll().stream()
                .filter(entity -> entity.getType() == AnalysisType.OPPORTUNITY)
                .sorted((e1, e2) -> Double.compare(e2.getRiskScore(), e1.getRiskScore()))
                .limit(limit)
                .toList();
    }
}