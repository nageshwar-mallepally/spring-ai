import React, { useState } from "react";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import {
  AppBar,
  Toolbar,
  Typography,
  Container,
  Grid,
  Paper,
  Box,
} from "@mui/material";
import RiskOpportunityAnalyzer from "./components/RiskOpportunityAnalyzer";
import AnalysisHistory from "./components/AnalysisHistory";
import RagAnalyzer from "./components/RagAnalyzer";

const theme = createTheme({
  palette: {
    mode: "light",
    primary: {
      main: "#2c3e50",
    },
    secondary: {
      main: "#e74c3c",
    },
    success: {
      main: "#b7bcaeff",
    },
    warning: {
      main: "#f39c12",
    },
  },
  typography: {
    h5: {
      fontSize: "1.3rem",
      fontWeight: "bold",
    },
    h6: {
      fontSize: "1.1rem",
      fontWeight: "bold",
    },
  },
});

function App() {
  const [refreshHistory, setRefreshHistory] = useState(0);

  const handleAnalysisComplete = () => {
    setRefreshHistory((prev) => prev + 1);
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AppBar
        position="static"
        sx={{ background: "linear-gradient(45deg, #2c3e50 30%, #34495e 90%)" }}
      >
        <Toolbar variant="dense">
          <Typography
            variant="h6"
            component="div"
            sx={{ flexGrow: 1, fontWeight: "bold" }}
          >
            Risk & Opportunity AI RAG Analyst
          </Typography>
        </Toolbar>
      </AppBar>

      <Container
        maxWidth="xl"
        sx={{ mt: 1, mb: 1, p: 1, height: "calc(100vh - 64px)" }}
      >
        <Grid container spacing={3} sx={{ height: "100%" }}>
          {/* Left Column - AI Analysis (Same width as RAG) */}
          <Grid item xs={12} md={6} sx={{ height: "75%", minWidth: "49%" }}>
            <Paper
              sx={{
                p: 1,
                height: "100%",
                display: "flex",
                flexDirection: "column",
                border: "1px solid #3498db",
                borderRadius: "8px",
                background:
                  "linear-gradient(135deg, rgba(240, 255, 244, 1) 0%, #e6ffed 100%)",
              }}
            >
              <RiskOpportunityAnalyzer
                onAnalysisComplete={handleAnalysisComplete}
              />
            </Paper>
          </Grid>

          {/* Right Column - RAG Analysis (Same width as AI Analysis) */}
          <Grid item xs={12} md={6} sx={{ height: "75%", minWidth: "49%" }}>
            <Paper
              sx={{
                p: 1,
                height: "100%",
                display: "flex",
                flexDirection: "column",
                border: "1px solid #e74c3c",
                borderRadius: "8px",
                background: "linear-gradient(135deg, #fff5f5 0%, #ffeaea 100%)",
              }}
            >
              <AnalysisHistory refresh={refreshHistory} />
            </Paper>
          </Grid>

          {/* Bottom Row - Analysis History (Full width below both) */}
          <Grid item xs={12} sx={{ height: "100%", minWidth: "100%" }}>
            <Paper
              sx={{
                p: 1,
                height: "100%",
                display: "flex",
                flexDirection: "column",
                border: "1px solid #27ae60",
                borderRadius: "8px",
                background: "linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%)",
              }}
            >
              <RagAnalyzer />
            </Paper>
          </Grid>
        </Grid>
      </Container>
    </ThemeProvider>
  );
}

export default App;
