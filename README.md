# 💰 MoneySaver
MoneySaver is a responsive web application that provides a complete solution for personal finance management.
The application allows:

* User management (authentication, 2FA, and profile).

* Expense tracking (via manual entry, ticket/invoice recognition, and bank transactions).

* Expense categorization (predefined or custom categories).

* Savings goals definition.

* Expense visualization through interactive charts.
## 🖥️ Frontend
* Developed with React and Tailwind CSS.

* Uses Shadcn/UI components (https://ui.shadcn.com/) for a modern and consistent design.

## ⚙️ Backend
* Built with Java using Maven for dependency management.

* Implements REST API principles (not fully RESTful).

* Stateless architecture with communication between frontend and backend via JSON requests.

## 🗄️ Database

* Powered by PostgreSQL.

* Connected to the server using Spring Data JPA for efficient entity and relationship management.

### Conceptual diagram
<img width="993" height="472" alt="mco (1)" src="https://github.com/user-attachments/assets/5a40726f-a396-47e9-9eb6-e1c75427862f" />

## External API
This application web uses two external API:

### TrueLayer (https://truelayer.com/)
* Enables users to link their bank accounts and fetch transactions.

* In this project, the TrueLayer sandbox is used → all data is mock (not real).
### Google Cloud Vision (https://cloud.google.com/vision)
* Used to extract data from tickets or invoices via OCR (Optical Character Recognition).

