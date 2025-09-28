import React, { useState, useEffect } from "react";
import {
  Box,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Chip,
  IconButton,
  Alert,
  CircularProgress,
  Card,
  CardContent,
} from "@mui/material";
import { Delete, Visibility } from "@mui/icons-material";
import axios from "axios";

const AnalysisHistory = ({ refresh }) => {
  const [analyses, setAnalyses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchAnalyses = async () => {
    try {
      setLoading(true);
      const response = await axios.get(
        "http://localhost:8090/api/risk-opportunity"
      );
      setAnalyses(response.data);
      setError("");
    } catch (err) {
      setError("Failed to fetch analysis history: " + err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAnalyses();
  }, [refresh]);

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8090/api/risk-opportunity/${id}`);
      fetchAnalyses();
    } catch (err) {
      setError("Failed to delete analysis: " + err.message);
    }
  };

  const getTypeColor = (type) => {
    return type === "RISK" ? "error" : "success";
  };

  const getPriorityColor = (priority) => {
    switch (priority) {
      case "LOW":
        return "success";
      case "MEDIUM":
        return "warning";
      case "HIGH":
        return "error";
      case "CRITICAL":
        return "error";
      default:
        return "default";
    }
  };

  if (loading) {
    return (
      <Box
        display="flex"
        justifyContent="center"
        alignItems="center"
        height="100%"
      >
        <CircularProgress size={20} />
      </Box>
    );
  }

  return (
    <Box sx={{ height: "100%", display: "flex", flexDirection: "column" }}>
      <Typography
        variant="h6"
        sx={{
          fontWeight: "bold",
          color: "#27ae60",
          mb: 0.5,
          fontSize: "0.9rem",
        }}
      >
        Analysis History
      </Typography>

      {error && (
        <Alert severity="error" sx={{ py: 0, fontSize: "0.7rem", mb: 0.5 }}>
          {error}
        </Alert>
      )}

      {analyses.length === 0 ? (
        <Card
          sx={{
            flex: 1,
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <CardContent sx={{ textAlign: "center" }}>
            <Typography
              variant="body2"
              color="textSecondary"
              sx={{ fontSize: "0.7rem" }}
            >
              No analyses found. Start by analyzing a risk or opportunity above.
            </Typography>
          </CardContent>
        </Card>
      ) : (
        <TableContainer component={Paper} sx={{ flex: 1, overflow: "auto" }}>
          <Table stickyHeader size="small">
            <TableHead>
              <TableRow>
                <TableCell
                  sx={{
                    fontWeight: "bold",
                    background: "#8eb9a0ff",
                    color: "black",
                    fontSize: "0.6rem",
                    py: 0.3,
                    px: 0.5,
                  }}
                >
                  Title
                </TableCell>
                <TableCell
                  sx={{
                    fontWeight: "bold",
                    background: "#8eb9a0ff",
                    color: "black",
                    fontSize: "0.6rem",
                    py: 0.3,
                    px: 0.5,
                  }}
                >
                  Type
                </TableCell>
                <TableCell
                  sx={{
                    fontWeight: "bold",
                    background: "#8eb9a0ff",
                    color: "black",
                    fontSize: "0.6rem",
                    py: 0.3,
                    px: 0.5,
                  }}
                >
                  Category
                </TableCell>
                <TableCell
                  sx={{
                    fontWeight: "bold",
                    background: "#8eb9a0ff",
                    color: "black",
                    fontSize: "0.6rem",
                    py: 0.3,
                    px: 0.5,
                  }}
                >
                  Impact
                </TableCell>
                <TableCell
                  sx={{
                    fontWeight: "bold",
                    background: "#8eb9a0ff",
                    color: "black",
                    fontSize: "0.6rem",
                    py: 0.3,
                    px: 0.5,
                  }}
                >
                  Probability
                </TableCell>
                <TableCell
                  sx={{
                    fontWeight: "bold",
                    background: "#8eb9a0ff",
                    color: "black",
                    fontSize: "0.6rem",
                    py: 0.3,
                    px: 0.5,
                  }}
                >
                  Risk Score
                </TableCell>
                <TableCell
                  sx={{
                    fontWeight: "bold",
                    background: "#8eb9a0ff",
                    color: "black",
                    fontSize: "0.6rem",
                    py: 0.3,
                    px: 0.5,
                  }}
                >
                  Priority
                </TableCell>
                <TableCell
                  sx={{
                    fontWeight: "bold",
                    background: "#8eb9a0ff",
                    color: "black",
                    fontSize: "0.6rem",
                    py: 0.3,
                    px: 0.5,
                  }}
                >
                  Date
                </TableCell>
                <TableCell
                  sx={{
                    fontWeight: "bold",
                    background: "#8eb9a0ff",
                    color: "black",
                    fontSize: "0.6rem",
                    py: 0.3,
                    px: 0.5,
                  }}
                >
                  Actions
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {analyses.map((analysis) => (
                <TableRow key={analysis.id} hover>
                  <TableCell sx={{ fontSize: "0.6rem", py: 0.3, px: 0.5 }}>
                    <Typography variant="body2" fontWeight="medium">
                      {analysis.title}
                    </Typography>
                  </TableCell>
                  <TableCell sx={{ py: 0.3, px: 0.5 }}>
                    <Chip
                      label={analysis.type}
                      color={getTypeColor(analysis.type)}
                      size="small"
                      variant="filled"
                      sx={{ fontSize: "0.55rem", height: 18 }}
                    />
                  </TableCell>
                  <TableCell sx={{ fontSize: "0.6rem", py: 0.3, px: 0.5 }}>
                    {analysis.category}
                  </TableCell>
                  <TableCell sx={{ fontSize: "0.6rem", py: 0.3, px: 0.5 }}>
                    {analysis.impactScore}
                  </TableCell>
                  <TableCell sx={{ fontSize: "0.6rem", py: 0.3, px: 0.5 }}>
                    {(analysis.probability * 100).toFixed(1)}%
                  </TableCell>
                  <TableCell sx={{ py: 0.3, px: 0.5 }}>
                    <Chip
                      label={analysis.riskScore?.toFixed(2)}
                      variant="outlined"
                      size="small"
                      color="primary"
                      sx={{ fontSize: "0.55rem", height: 18 }}
                    />
                  </TableCell>
                  <TableCell sx={{ py: 0.3, px: 0.5 }}>
                    <Chip
                      label={analysis.priority}
                      color={getPriorityColor(analysis.priority)}
                      size="small"
                      variant="filled"
                      sx={{ fontSize: "0.55rem", height: 18 }}
                    />
                  </TableCell>
                  <TableCell sx={{ fontSize: "0.6rem", py: 0.3, px: 0.5 }}>
                    {new Date(analysis.createdAt).toLocaleDateString()}
                  </TableCell>
                  <TableCell sx={{ py: 0.3, px: 0.5 }}>
                    <IconButton
                      size="small"
                      color="primary"
                      sx={{ fontSize: "2rem" }}
                      onClick={() => alert(`Details: ${analysis.aiAnalysis}`)}
                    >
                      <Visibility fontSize="midium" />
                    </IconButton>
                    <IconButton
                      size="small"
                      color="error"
                      sx={{ fontSize: "2rem" }}
                      onClick={() => {
                        if (
                          window.confirm(
                            "Are you sure you want to delete this analysis?"
                          )
                        ) {
                          handleDelete(analysis.id);
                        }
                      }}
                    >
                      <Delete fontSize="midium" />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}

      {analyses.length > 0 && (
        <Box mt={0.3}>
          <Typography
            variant="body2"
            color="textSecondary"
            sx={{ fontWeight: "bold", fontSize: "0.7rem" }}
          >
            Total analysis: {analyses.length}
          </Typography>
        </Box>
      )}
    </Box>
  );
};

export default AnalysisHistory;
