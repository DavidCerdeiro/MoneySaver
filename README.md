# 💰 MoneySaver

[![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](#)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](#)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](#)

*A comprehensive personal finance management solution, developed as my Bachelor's Thesis in Computer Engineering.*

**[🔴 VISIT THE DEPLOYED APPLICATION HERE]** *[(Link)](https://moneysaver-pi.vercel.app/)*

> **Are you a recruiter? Try the app without signing up:** > I have implemented a **Demo User** system. Upon access, the system automatically generates a cloned environment with realistic test data so you can fully explore the platform. (For security purposes, these environments are automatically destroyed after 24 hours).

## 🌟 Main Features

* **Fully Responsive Design:** The interface dynamically adapts to mobile, tablet, and desktop views, intelligently rearranging UI elements to guarantee an optimal and seamless User Experience (UX) on any device.
* **Open Banking Integration:** Simulates bank account linking and transaction fetching using the **TrueLayer Sandbox** API, providing a realistic demonstration of financial data aggregation with mock data.
* **Invoice Recognition (OCR):** Upload a receipt or invoice, and the system will automatically extract the amount and merchant details via **Google Cloud Vision**.
* **Secure User Management:** Full authentication flow with 2FA and integrated email notifications powered by the **Brevo** API.
* **Data Visualization:** Interactive charts to understand your spending habits and savings goals progress at a glance.
* **Ephemeral Accounts System:** Backend architecture that allows for the automatic generation and cleanup (via CRON jobs) of demo accounts for evaluation purposes.

## 📸 User Interface
<img width="1635" height="944" alt="Captura de pantalla 2026-04-13 173907" src="https://github.com/user-attachments/assets/48e715d3-25ed-47b7-b283-5629f620ff7d" />
<img width="1604" height="938" alt="Captura de pantalla 2026-04-13 174623" src="https://github.com/user-attachments/assets/c2ba5535-2fe2-4879-afcc-aa7202cdaa8e" />

## ⚙️ Tech Stack & Architecture

### Frontend
* **Framework:** React with Tailwind CSS.
* **UI Components:** Shadcn/UI for a modern, accessible, and consistent design.

### Backend
* **Core:** Java and Spring Boot (Maven).
* **Architecture:** Stateless REST API.
* **Testing:** High coverage with unit and integration tests (JUnit 5 & Spring Boot Test).

### Database
* **Engine:** PostgreSQL.
* **ORM:** Spring Data JPA for efficient entity and relationship management.

## 🚀 Local Deployment (Getting Started)

Follow these steps to run the MoneySaver environment on your local machine.

### Prerequisites
Make sure you have the following installed on your system:
* **Java JDK** (17 or higher recommended) & **Maven**
* **Node.js** & **npm**
* **PostgreSQL** (Running on port `5432`)

### 1. Database Setup
1. Create a PostgreSQL database named `tfg`. The default configuration expects the username `postgres` and password `usuario` (you can modify this in the `application.properties` file if needed).
2. Navigate to the database folder:
   ```bash
   cd MoneySaver/database
   ```
3. Execute the SQL scripts in the following strict order to avoid constraint errors:
* moneySaver.sql (Creates tables and indexes)
* definition_triggers.sql (Creates the necessary database triggers)
* mock_data.sql (Populates the system with initial test data)

### 2. Backend Setup
The backend requires some external API keys to function correctly.
1. Navigate to the backend directory:
```bash
cd MoneySaver/backend
```

2. Set up your environment variables. You will need to export the following variables in your terminal or IDE environment:
```bash
export BREVO_API_KEY="your_brevo_api_key_here"
export BREVO_SENDER_EMAIL="your_verified_brevo_email_here"
export TRUELAYER_CLIENT_SECRET="your_truelayer_secret_here"
# Required for Google Cloud Vision (OCR) authentication
export GOOGLE_APPLICATION_CREDENTIALS="/path/to/your/google-service-account-file.json"
```

3. Start the Spring Boot application:
```bash
mvn spring-boot:run
```

The backend will start running on `http://localhost:8081`.

### 3. Frontend Setup
1. Open a new terminal instance and navigate to the frontend directory:
```bash
cd MoneySaver/frontend/React
```

2. Install the necessary Node dependencies:
```bash
npm install
```

3. Start the React development server:
```bash
npm run dev
```

The frontend will be available at `http://localhost:5173`. (This port matches the TrueLayer callback URL configuration).

⚠️ **Note on External APIs**: If you do not configure the environment variables for Brevo, TrueLayer, or Google Cloud, the application will still compile and run, but specific features (like email notifications, bank synchronization, and OCR receipt scanning) will throw errors when triggered.
