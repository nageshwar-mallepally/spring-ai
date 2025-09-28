package com.agentic.riskai.ro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadRequest {
    
    @NotBlank(message = "Content is required")
    private String content;
    
    private String contentType = "text/plain";
    
    private Map<String, Object> metadata = new HashMap<>();
}