package com.agentic.riskai.ro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskAnalysisResult {

    private String category;
    private Double impactScore;
    private Double probability;
    private String priority;
    private String detailedAnalysis;
    private String mitigationStrategy;
    private String exploitationPlan;
    private List<String> keyFactors;

    public RiskAnalysisResult(String category, double impactLevel, double probability, String priority,
                              String analysis, String mitigationStrategy, String exploitationPlan, List<String> factors) {
        this.category = category;
        this.impactScore = impactLevel;
        this.probability = probability;
        this.priority = priority;
        this.detailedAnalysis = analysis;
        this.mitigationStrategy = mitigationStrategy;
        this.exploitationPlan = exploitationPlan;
        this.keyFactors = factors;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(Double impactScore) {
        this.impactScore = impactScore;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDetailedAnalysis() {
        return detailedAnalysis;
    }

    public void setDetailedAnalysis(String detailedAnalysis) {
        this.detailedAnalysis = detailedAnalysis;
    }

    public String getMitigationStrategy() {
        return mitigationStrategy;
    }

    public void setMitigationStrategy(String mitigationStrategy) {
        this.mitigationStrategy = mitigationStrategy;
    }

    public String getExploitationPlan() {
        return exploitationPlan;
    }

    public void setExploitationPlan(String exploitationPlan) {
        this.exploitationPlan = exploitationPlan;
    }

    public List<String> getKeyFactors() {
        return keyFactors;
    }

    public void setKeyFactors(List<String> keyFactors) {
        this.keyFactors = keyFactors;
    }

}