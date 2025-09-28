import React, { useState, useEffect } from "react";
import {
  Box,
  TextField,
  Button,
  Typography,
  Alert,
  Card,
  CardContent,
  Chip,
  CircularProgress,
} from "@mui/material";
import axios from "axios";

const RagAnalyzer = () => {
  const [query, setQuery] = useState("");
  const [analysis, setAnalysis] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [ragStatus, setRagStatus] = useState({
    documentCount: 0,
    status: "inactive",
  });

  useEffect(() => {
    fetchRagStatus();
  }, []);

  const fetchRagStatus = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8090/api/risk-opportunity/rag-status"
      );
      setRagStatus(response.data);
    } catch (err) {
      console.error("Failed to fetch RAG status", err);
    }
  };

  const handleAnalyze = async () => {
    if (!query.trim()) return;

    setLoading(true);
    setError("");
    setAnalysis("");

    try {
      const response = await axios.post(
        "http://localhost:8090/api/risk-opportunity/rag-analysis",
        query,
        {
          headers: { "Content-Type": "text/plain" },
        }
      );
      setAnalysis(response.data);
      fetchRagStatus();
    } catch (err) {
      setError("Analysis failed: " + (err.response?.data || err.message));
    } finally {
      setLoading(false);
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      handleAnalyze();
    }
  };

  return (
    <Box
      sx={{ display: "flex", flexDirection: "column", height: "100%", gap: 1 }}
    >
      <Typography variant="h6" sx={{ fontWeight: "bold", color: "#27ae60" }}>
        RAG Analysis
      </Typography>

      <Card
        sx={{ background: "linear-gradient(45deg, #ffeaa7 30%, #fab1a0 90%)" }}
      >
        <CardContent sx={{ p: 1, "&:last-child": { pb: 1 } }}>
          <Typography variant="subtitle2" sx={{ fontWeight: "bold", mb: 0.5 }}>
            RAG Status
          </Typography>
          <Box display="flex" gap={0.5} flexWrap="wrap">
            <Chip
              label={`Documents: ${ragStatus.documentCount}`}
              variant="filled"
              color="primary"
              size="small"
            />
            <Chip
              label={`Status: ${ragStatus.status.toUpperCase()}`}
              color={ragStatus.status === "active" ? "success" : "warning"}
              variant="filled"
              size="small"
            />
          </Box>
        </CardContent>
      </Card>

      {error && (
        <Alert severity="error" sx={{ py: 0, fontSize: "0.8rem" }}>
          {error}
        </Alert>
      )}

      <Box sx={{ display: "flex", gap: 1 }}>
        <TextField
          fullWidth
          label="Ask about risks, opportunities, mitigation strategies, etc."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          multiline
          rows={4}
          size="small"
          placeholder="e.g., What are common risk mitigation strategies? How can I identify opportunities in emerging markets?"
          disabled={loading}
          required
        />

        <Button
          variant="contained"
          onClick={handleAnalyze}
          disabled={loading || !query.trim()}
          sx={{
            // background: "linear-gradient(45deg, #df5089ff 30%, #540524ff 90%)",
            background: "linear-gradient(45deg, #3498db 30%, #123c58ff 90%)",
            color: "#000 !important",
            fontWeight: "bold",
            fontSize: "0.9rem",
            px: 1.5,
            minWidth: "150px",
            height: "43%",
            alignSelf: "flex-start",
            whiteSpace: "nowrap",
            textTransform: "none",
            marginTop: "60px",
          }}
          size="small"
          startIcon={loading ? <CircularProgress size={16} /> : null}
        >
          {loading ? "Analyzing..." : "RAG Analyze"}
        </Button>
      </Box>
      {/* Result area that takes remaining space */}
      <Card
        sx={{
          border: "1px solid #bdc3c7",
          flex: 1,
          overflow: "auto",
          minHeight: "150px",
          background: "linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%)",
        }}
      >
        <CardContent
          sx={{
            p: 1,
            "&:last-child": { pb: 1 },
            height: 350,
            display: "flex",
            flexDirection: "column",
          }}
        >
          <Typography
            variant="subtitle2"
            sx={{ fontWeight: "bold", color: "#27ae60", mb: 0.5 }}
          >
            RAG Analysis Result
          </Typography>

          <Box
            sx={{
              flex: 1,
              width: "100%",
              maxWidth: "100%",
              overflow: "hidden", // prevents expanding
              display: "flex",
              flexDirection: "column",
            }}
          >
            <Box
              sx={{
                flex: 1,
                overflowY: "auto",
                overflowX: "auto", // ✅ allow scroll instead of stretch
                maxWidth: "100%",
                width: "100%",
                p: 1,
                bgcolor: "#fafafa",
                borderRadius: 1,
                border: "1px solid #ddd",
              }}
            >
              <Typography
                component="pre"
                variant="body2"
                sx={{
                  whiteSpace: "pre-wrap", // ✅ preserve newlines, wrap lines
                  wordBreak: "break-word", // ✅ break long words
                  overflowWrap: "break-word", // ✅ ensure browser compatibility
                  color: "black",
                  fontSize: "0.85rem",
                  m: 0,
                  width: "100%",
                  maxWidth: "100%",
                }}
              >
                {analysis ||
                  'Enter a query above and click "Analyze with RAG" to see results here...'}
              </Typography>
            </Box>
          </Box>
        </CardContent>
      </Card>
    </Box>
  );
};

export default RagAnalyzer;
