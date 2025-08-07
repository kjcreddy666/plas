# LOANFLOW

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Vue.js](https://img.shields.io/badge/Vue.js-3.5.18-green.svg)](https://vuejs.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue.svg)](https://www.postgresql.org/)

## 📋 Overview

The Personal Loan Application System (PLAS) is a comprehensive full-stack web application designed to streamline the personal loan application process. Built with modern technologies, it provides an intuitive interface for customers to apply for loans and a powerful administrative dashboard for loan management.

## 🚀 Key Features

### For Customers
- **Secure User Registration & Authentication** - JWT-based authentication system
- **Loan Application Management** - Submit and track loan applications
- **EMI Calculator** - Real-time calculation with detailed repayment schedules
- **Application Status Tracking** - Monitor application progress in real-time
- **Support Ticket System** - Raise and track support requests

### For Administrators
- **Comprehensive Dashboard** - Overview of all system activities
- **Loan Application Review** - Approve, reject, or request additional information
- **User Management** - Manage customer accounts and profiles
- **Support Ticket Management** - Handle customer queries efficiently

## 🏗️ System Architecture

### Backend Architecture
- **Framework**: Spring Boot 3.5.4 with Java 17
- **Database**: PostgreSQL with JPA/Hibernate ORM
- **Security**: Spring Security with JWT authentication
- **Build System**: Maven for dependency management
- **Testing**: JUnit 5 and Mockito for comprehensive testing

### Frontend Architecture
- **Framework**: Vue.js 3.5.18 with Composition API
- **Build Tool**: Vite 7.0.6 for fast development and optimized builds
- **Styling**: Bootstrap 5 for responsive design
- **HTTP Client**: Axios for API communication
- **Routing**: Vue Router for single-page application navigation

### 📝 Key Design Patterns Used

#### 1. **Repository Pattern**:

- Abstracts data access logic
- Provides clean separation between business logic and data layer

#### 2. **DTO Pattern**:

- Data Transfer Objects for API communication
- Prevents over-exposure of internal entities

#### 3. **Builder Pattern**:

- Used in entity creation (Lombok @Builder)
- Fluent API for object construction

#### 4. **Dependency Injection**:

- Spring's IoC container
- Constructor injection with @RequiredArgsConstructor

#### 5. **Composition API Pattern** (Frontend):

- Reusable business logic with composables
- Better code organization and testing

---

## 💼 Business Logic

### Loan Application Workflow
1. **User Registration/Authentication** - Secure account creation and login
2. **Application Submission** - Comprehensive loan application form
3. **Validation & Processing** - Automated validation with business rules
4. **Admin Review** - Manual review and decision-making process
5. **Status Updates** - Real-time status tracking
6. **EMI Calculation** - Automated calculation and schedule generation

### Key Business Rules
- **One Application Per Day** - Prevents duplicate applications
- **Credit Score Validation** - Ensures score is within 300-850 range
- **Minimum Tenure** - 6-month minimum loan tenure
- **Role-Based Access** - Separate interfaces for customers and administrators

## 🔐 Security Features

### Authentication & Authorization
- **JWT Token-Based Authentication** - Stateless and secure
- **Role-Based Access Control** - Customer and Admin roles
- **Password Encryption** - BCrypt hashing for secure storage
- **Session Management** - Secure session handling

### Data Protection
- **Input Validation** - Comprehensive server and client-side validation
- **SQL Injection Prevention** - JPA/Hibernate protection
- **CORS Configuration** - Secure cross-origin resource sharing

## 📊 Database Schema

### Core Entities
- **Users** - Customer and admin user information
- **Loan Applications** - Complete loan application data
- **Support Tickets** - Customer support and query management
- **Repayment Schedule** - EMI calculation and payment tracking

### Key Relationships
- **One-to-Many**: Users to Loan Applications
- **One-to-Many**: Users to Support Tickets
- **One-to-Many**: Loan Applications to Repayment Schedule
- **Many-to-One**: Admin reviews to Loan Applications

## 🌐 API Documentation

### RESTful Endpoints
The system provides comprehensive REST APIs for:
- **Authentication**: Login, registration, and logout
- **Loan Management**: Application submission and tracking
- **Admin Operations**: Review, approval, and user management
- **EMI Calculations**: Preview and schedule generation
- **Support System**: Ticket creation and management

### Response Format
- **Consistent JSON responses** across all endpoints
- **Proper HTTP status codes** for different scenarios
- **Error handling** with descriptive error messages
- **Pagination support** for large datasets

## 🧪 Testing Strategy

### Comprehensive Test Coverage
- **Unit Testing** - Individual component testing with Mockito
- **Controller Testing** - HTTP endpoint validation
- **Service Testing** - Business logic verification

### Quality Assurance
- **Code Coverage** - Comprehensive test coverage reporting
- **Automated Testing** - CI/CD pipeline integration
- **Performance Testing** - Load and stress testing
- **Security Testing** - Vulnerability assessment

## 📈 Performance Features

### Frontend Optimizations
- **Lazy Loading** - On-demand component loading
- **Bundle Optimization** - Vite-powered build optimization
- **API Caching** - Response caching for better performance
- **Component Reusability** - Modular component design

### Containerization
- **Docker Support** - Containerized application deployment
- **Multi-stage Builds** - Optimized image sizes
- **Health Checks** - Container monitoring and management

## 📋 Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 20.19.0 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher

### Quick Start
1. **Clone the repository**
2. **Configure database connection**
3. **Install dependencies**
4. **Run backend application**
5. **Start frontend development server**
6. **Access the application**

## 🔧 Project Setup

#

#### Update Application Properties
Navigate to `backend/src/main/resources/application.properties` and update:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/plas1
spring.datasource.username=plas_user
spring.datasource.password=your_password
```

#### Run Backend Application
```bash
# Run the Spring Boot application
./mvnw spring-boot:run

# Alternative: Run the JAR file
java -jar target/plas-0.0.1-SNAPSHOT.jar
```

The backend will start on `http://localhost:8080`

#### Install Dependencies
```bash
# Install npm dependencies
npm install

# Alternative: Use yarn if preferred
yarn install
```

The frontend will start on `http://localhost:5173`

#### Docker Setup
```bash
# Build and run with Docker Compose (if docker-compose.yml exists)
docker-compose up --build

# Or build individual containers
cd backend
docker build -t plas-backend .

cd ../frontend
docker build -t plas-frontend .
```

#### Check Backend Health
```bash
# Test backend API
curl http://localhost:8080/actuator/health
```

#### Access Applications
- **Frontend**: http://localhost:5173
- **Backend API**: http://localhost:8080
- **Alternative Frontend (loan-flow)**: http://localhost:5174

---

**LOANFLOW**  
*Streamlining the loan application process with modern technology*