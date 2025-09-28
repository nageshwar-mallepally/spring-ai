package com.agentic.riskai.ro.training;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.Map;

import com.agentic.riskai.ro.service.RagService;

@Slf4j
@Component
public class DataLoader {

    private final RagService ragService;

    public DataLoader(RagService ragService) {
        this.ragService = ragService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadSampleData() {
        log.info("Loading sample risk/opportunity documents for RAG...");

        // Sample risk management documents
        String[] sampleDocs = {
            """
            Project Risk Management: Risks are uncertain events that can positively or negatively impact project objectives. 
            Common risk categories include technical, external, organizational, and project management risks.
            Risk assessment involves evaluating probability and impact to determine priority.
            """,
            
            """
            Opportunity Management: Opportunities are uncertain events that can benefit project objectives.
            Key opportunity categories include cost savings, schedule acceleration, performance improvement, and stakeholder satisfaction.
            Opportunity assessment should consider feasibility and potential benefits.
            """,
            
            """
            Mitigation Strategies: For high-priority risks, consider avoidance, transfer, mitigation, or acceptance.
            Risk avoidance involves changing project plan to eliminate risk. Risk transfer shifts impact to third party.
            Risk mitigation reduces probability or impact. Risk acceptance involves acknowledging risk without action.
            """,
            
            """
            Exploitation Strategies: For opportunities, consider exploit, share, enhance, or accept.
            Exploitation ensures opportunity is realized. Sharing involves partnering with third party.
            Enhancement increases probability or impact. Acceptance involves not pursuing opportunity actively.
            """
        };
        
        for (String doc : sampleDocs) {
            ragService.storeDocument(doc, "risk_management", Map.of("source", "sample_data"));
        }
        
        log.info("Loaded {} sample documents", sampleDocs.length);
    }
}