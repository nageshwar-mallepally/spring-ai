markdown
# Risk & Opportunity AI RAG Analyst

A full-stack application for AI-powered risk assessment and opportunity analysis using RAG technology.

## 🚀 Overview

This project provides intelligent risk and opportunity analysis through a modern web interface. It combines traditional risk assessment with AI-powered insights using Retrieval-Augmented Generation (RAG) technology.

## 📋 Features

### AI Analysis
- Risk and opportunity identification
- Probability scoring and impact assessment
- Priority classification (LOW, MEDIUM, HIGH, CRITICAL)
- Real-time analysis with historical tracking

### RAG Integration
- Contextual document retrieval and analysis
- Intelligent recommendations based on knowledge base
- Real-time RAG status monitoring
- Evidence-based insights generation

### Dashboard
- Responsive Material-UI design
- Interactive analysis history
- Side-by-side comparison view
- Mobile-friendly interface

## 🛠️ Tech Stack

**Frontend:**
- React.js with Material-UI
- Axios for API communication
- Responsive grid layout

**Backend:**
- Spring Boot with H2 database
- RESTful API endpoints
- RAG integration service
- JPA for data persistence

## 🏃‍♂️ Quick Start

### Prerequisites
- Java 17+
- Node.js 16+
- npm or yarn

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd risk-opportunity-ai-analyst
Start Backend (Spring Boot)

bash
cd backend
./mvnw spring-boot:run
Backend runs on http://localhost:8090

Start Frontend (React)

bash
cd frontend
npm install
npm start
Frontend runs on http://localhost:3000

Default Access
Application: http://localhost:3000

API Documentation: http://localhost:8090/api-docs

H2 Database Console: http://localhost:8090/h2-console

JDBC URL: jdbc:h2:mem:testdb

Username: sa

Password: password

📊 Core Functionality
Risk/Opportunity Analysis
Submit titles and descriptions for AI analysis

Get probability scores and risk assessments

View categorized results with priority levels

RAG Analysis
Query the knowledge base for contextual insights

Receive structured recommendations

Monitor RAG system status and document count

Analysis History
Track all previous analyses

Filter by type, category, and priority

Export and manage historical data

🗄️ Database Schema
The H2 database includes:

analysis_entity: Stores risk/opportunity analyses

Automatic schema creation on startup

In-memory storage with persistence options

🔧 API Endpoints
POST /api/risk-opportunity/analyze - Analyze risk/opportunity

GET /api/risk-opportunity - Get analysis history

POST /api/risk-opportunity/rag-analysis - RAG query analysis

GET /api/risk-opportunity/rag-status - RAG system status

🎯 Usage Example
AI Analysis: Enter risk/opportunity details to get AI assessment

RAG Query: Ask contextual questions about risks and mitigation

History Review: Track and compare previous analyses

Export Insights: Use recommendations for decision making

🤝 Contributing
Fork the repository

Create feature branch (git checkout -b feature/improvement)

Commit changes (git commit -m 'Add feature')

Push to branch (git push origin feature/improvement)

Open Pull Request

📄 License
MIT License - see LICENSE file for details.