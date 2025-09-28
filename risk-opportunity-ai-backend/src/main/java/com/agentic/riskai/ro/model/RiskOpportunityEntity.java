package com.agentic.riskai.ro.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "risk_opportunities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskOpportunityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private AnalysisType type; // RISK or OPPORTUNITY
    
    private String title;
    private String description;
    
    @Column(length = 2000)
    private String aiAnalysis;
    
    private String category;
    private Double impactScore; // 1-10
    private Double probability; // 0-1
    private Double riskScore; // impact * probability
    
    @Enumerated(EnumType.STRING)
    private PriorityLevel priority;
    
    private String mitigationStrategy;
    private String exploitationPlan;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}