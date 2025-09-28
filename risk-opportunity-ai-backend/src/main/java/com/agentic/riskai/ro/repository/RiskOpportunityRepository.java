package com.agentic.riskai.ro.repository;

import com.agentic.riskai.ro.model.RiskOpportunityEntity;
import com.agentic.riskai.ro.model.AnalysisType;
import com.agentic.riskai.ro.model.PriorityLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskOpportunityRepository extends JpaRepository<RiskOpportunityEntity, Long> {
    
    List<RiskOpportunityEntity> findByTypeOrderByCreatedAtDesc(AnalysisType type);
    List<RiskOpportunityEntity> findByPriorityOrderByRiskScoreDesc(PriorityLevel priority);
    boolean existsByTitleAndDescription(String title, String description);
    
}