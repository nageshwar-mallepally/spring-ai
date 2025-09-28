import React, { useState } from "react";
import {
  Box,
  TextField,
  Button,
  Typography,
  Alert,
  Card,
  CardContent,
  FormControlLabel,
  Radio,
  RadioGroup,
} from "@mui/material";
import axios from "axios";

const RiskOpportunityAnalyzer = ({ onAnalysisComplete }) => {
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    type: "RISK",
  });
  const [analysis, setAnalysis] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.title.trim() || !formData.description.trim()) return;

    setLoading(true);
    setError("");

    try {
      const response = await axios.post(
        "http://localhost:8090/api/risk-opportunity/analyze",
        formData
      );
      setAnalysis(response.data);
      onAnalysisComplete();
    } catch (err) {
      setError("Analysis failed: " + err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (field, value) => {
    setFormData((prev) => ({
      ...prev,
      [field]: value,
    }));
  };

  return (
    <Box
      sx={{ display: "flex", flexDirection: "column", height: "100%", gap: 1 }}
    >
      <Typography variant="h6" sx={{ fontWeight: "bold", color: "#27ae60" }}>
        AI Analysis
      </Typography>

      {error && (
        <Alert severity="error" sx={{ py: 0, fontSize: "0.8rem" }}>
          {error}
        </Alert>
      )}

      <Box
        component="form"
        onSubmit={handleSubmit}
        sx={{ flex: 1, display: "flex", flexDirection: "column", gap: 1 }}
      >
        <TextField
          fullWidth
          label="Title"
          value={formData.title}
          onChange={(e) => handleChange("title", e.target.value)}
          size="small"
          required
        />

        <Box>
          <Typography variant="subtitle2" sx={{ fontWeight: "bold", mb: 0.5 }}>
            Analysis Type
          </Typography>
          <RadioGroup
            row
            value={formData.type}
            onChange={(e) => handleChange("type", e.target.value)}
            sx={{ gap: 2, justifyContent: "center" }}
          >
            <FormControlLabel
              value="RISK"
              control={<Radio size="small" color="primary" />}
              label="Risk"
            />
            <FormControlLabel
              value="OPPORTUNITY"
              control={<Radio size="small" color="success" />}
              label="Opportunity"
            />
          </RadioGroup>
        </Box>

        <Box sx={{ display: "flex", gap: 1 }}>
          <TextField
            fullWidth
            label="Description"
            value={formData.description}
            onChange={(e) => handleChange("description", e.target.value)}
            multiline
            rows={3}
            size="small"
            required
          />

          <Button
            type="submit"
            variant="contained"
            disabled={
              loading || !formData.title.trim() || !formData.description.trim()
            }
            sx={{
              background: "linear-gradient(45deg, #3498db 30%, #123c58ff 90%)",
              color: "#000 !important",
              fontWeight: "bold",
              fontSize: "0.9rem",
              px: 1.5,
              minWidth: "150px",
              height: "50%",
              alignSelf: "flex-start",
              whiteSpace: "nowrap",
              textTransform: "none",
              marginTop: "40px",
            }}
            size="small"
          >
            {loading ? "Analyzing..." : "AI Analyze"}
          </Button>
        </Box>

        {/* Result area that takes remaining space - matches RAG analyzer */}
        <Card
          sx={{
            border: "1px solid #bdc3c7",
            flex: 1,
            overflow: "auto",
            minHeight: "150px",
          }}
        >
          <CardContent sx={{ p: 1, "&:last-child": { pb: 1 }, height: "100%" }}>
            <Typography
              variant="subtitle2"
              sx={{ fontWeight: "bold", color: "#2c3e50", mb: 0.5 }}
            >
              AI Analysis Result
            </Typography>
            <Box
              sx={{
                height: "calc(100% - 30px)",
                overflow: "auto",
                fontSize: "0.8rem",
                lineHeight: 1.4,
              }}
            >
              {analysis ? (
                <>
                  <Typography variant="body2" sx={{ mb: 0.5 }}>
                    <strong>Category:</strong> {analysis.category}
                  </Typography>
                  <Typography variant="body2" sx={{ mb: 0.5 }}>
                    <strong>Impact Score:</strong> {analysis.impactScore}/10
                  </Typography>
                  <Typography variant="body2" sx={{ mb: 0.5 }}>
                    <strong>Probability:</strong>{" "}
                    {(analysis.probability * 100).toFixed(1)}%
                  </Typography>
                  <Typography variant="body2" sx={{ mb: 0.5 }}>
                    <strong>Priority:</strong> {analysis.priority}
                  </Typography>
                  <Typography variant="body2">
                    <strong>Risk Score:</strong>{" "}
                    {analysis.riskScore?.toFixed(2)}
                  </Typography>
                </>
              ) : (
                <Typography variant="body2" color="textSecondary">
                  Enter analysis details above and click "Analyze with AI" to
                  see results here...
                </Typography>
              )}
            </Box>
          </CardContent>
        </Card>
      </Box>
    </Box>
  );
};

export default RiskOpportunityAnalyzer;
