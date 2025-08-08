# Personal Loan Application System (PLAS)

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Vue.js](https://img.shields.io/badge/Vue.js-3.5.18-green.svg)](https://vuejs.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)](https://www.docker.com/)

## 📋 Overview

The Personal Loan Application System (PLAS) is a comprehensive full-stack web application designed to streamline the personal loan application process. Built with modern technologies, it provides an intuitive interface for customers to apply for loans and a powerful administrative dashboard for loan management.

## 🚀 Quick Start with Docker

### Prerequisites
- Docker Desktop installed
- Docker Compose available
- 4GB RAM minimum
- 10GB free disk space

### One-Command Deployment
```bash
# Clone the repository
git clone <repository-url>
cd plas

# Start the entire application stack
docker-compose up --build -d

# Access the application
# Frontend: http://localhost:3000
# Backend API: http://localhost:8080
# Database: localhost:5432
```

### Verify Deployment
```bash
# Check all services are running
docker-compose ps

# View application logs
docker-compose logs -f

# Test the application
curl http://localhost:3000  # Frontend
curl http://localhost:8080/actuator/health  # Backend
```

### Automated Setup Script
For even easier deployment, use the provided start script:
```bash
# Make executable and run
chmod +x start.sh
./start.sh

# Available commands
./start.sh start    # Start all services (default)
./start.sh stop     # Stop all services
./start.sh restart  # Restart all services
./start.sh status   # Show service status
./start.sh logs     # View all logs
./start.sh health   # Run health checks
./start.sh cleanup  # Remove all containers and data
```

### Service URLs & Access
- **Frontend Application**: http://localhost:3000
- **Backend API**: http://localhost:8080
- **API Health Check**: http://localhost:8080/actuator/health
- **Database**: localhost:5432 (postgres/12@Batworld)

### Default Login Credentials
```bash
# Admin User
Email: admin@plas.com
Password: admin123

# Customer User
Email: customer@plas.com
Password: customer123
```

## 🐳 Docker Deployment Details

### Container Architecture
The application consists of three main services:

1. **PostgreSQL Database Container**
   - Image: `postgres:15-alpine`
   - Port: 5432
   - Auto-initializes with schema.sql
   - Health checks enabled

2. **Spring Boot Backend Container**
   - Built from source using Maven and OpenJDK 17
   - Port: 8080
   - Includes health check endpoint
   - Environment-based configuration

3. **Vue.js Frontend Container**
   - Multi-stage build (Node.js + Nginx)
   - Port: 3000 (Nginx on port 80 inside container)
   - API proxy configuration to backend
   - Production-optimized build

### Docker Configuration Files

#### docker-compose.yml
- Orchestrates all three services
- Defines networks and volumes
- Health checks and dependencies
- Environment variables

#### Backend Dockerfile
- Multi-stage build for optimization
- Maven dependency caching
- OpenJDK 17 runtime
- Spring profiles support

#### Frontend Dockerfile
- Node.js 20 for building
- Nginx Alpine for serving
- Production build optimization
- Custom nginx configuration

### Service Management Commands
```bash
# View service status
docker-compose ps

# Scale services (if needed)
docker-compose up -d --scale backend=2

# View resource usage
docker stats

# Execute commands in containers
docker-compose exec backend bash
docker-compose exec postgres psql -U postgres -d plas

# Database operations
docker-compose exec postgres pg_dump -U postgres plas > backup.sql
docker-compose exec -T postgres psql -U postgres plas < backup.sql
```

### Development Workflow
```bash
# Development with live reloading
# Backend changes
docker-compose build backend && docker-compose up -d backend

# Frontend changes
docker-compose build frontend && docker-compose up -d frontend

# Database schema changes
docker-compose down -v  # Removes data
docker-compose up -d    # Recreates with new schema
```

### Troubleshooting Docker Deployment
```bash
# Check logs for errors
docker-compose logs backend
docker-compose logs frontend
docker-compose logs postgres

# Restart specific service
docker-compose restart backend

# Clean rebuild (no cache)
docker-compose build --no-cache

# Port conflicts resolution
netstat -tulpn | grep :3000  # Check port usage

# Clean Docker resources
docker system prune
docker volume prune
```

### Production Considerations
- Use Docker secrets for sensitive data
- Implement SSL/TLS termination
- Add load balancer for multiple backend instances
- Set up monitoring and logging
- Configure backup strategies
- Implement health monitoring

For detailed Docker deployment instructions, see [DOCKER_DEPLOYMENT.md](DOCKER_DEPLOYMENT.md).

## 🏗️ System Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │    Backend      │    │   PostgreSQL    │
│   (Vue.js)      │◄──►│  (Spring Boot)  │◄──►│   Database      │
│   Port: 3000    │    │   Port: 8080    │    │   Port: 5432    │
│   (Nginx)       │    │                 │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🎯 Key Features

### Module 1: User Management & Authentication
- **Secure Registration/Login** - JWT-based authentication
- **Role-based Access Control** - Customer and Admin roles
- **Profile Management** - User profile CRUD operations

### Module 2: Loan Application & Tracking
- **Three-step Application Process**:
  1. Loan Details (amount, tenure, purpose)
  2. Financial Information (income, credit score)
  3. Review & Submit
- **Business Rule Validation**:
  - Minimum income: ₹25,000
  - Credit score: 300-900 range
  - Loan amount: ₹1,00,000 minimum
  - Duplicate prevention (24-hour window)
- **Status Tracking**: NEW → UNDER_REVIEW → APPROVED/REJECTED

### Module 3: Admin Dashboard & Processing
- **Loan Management**: Filter, review, approve/reject applications
- **Audit Trail**: Track reviewer, timestamp, remarks
- **Support Ticket Management**: Handle customer queries
- **Status Transitions**: Enforce business rules

### Module 4: EMI Calculator & Repayment Schedule
- **Real-time EMI Calculation**: Uses standard formula
- **Interactive Sliders**: Loan amount, tenure, interest rate
- **Detailed Schedule**: Month-wise principal, interest, balance
- **Visual Representation**: Pie chart for principal vs. interest

### Module 5: Customer Support System
- **Ticket Creation**: Subject, description, loan reference
- **Status Tracking**: OPEN → IN_PROGRESS → RESOLVED → CLOSED
- **Admin Response System**: Two-way communication
- **Workflow Management**: Customer resolves, admin closes

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.5.4
- **Language**: Java 17
- **Database**: PostgreSQL 15
- **ORM**: JPA/Hibernate
- **Security**: Spring Security + JWT
- **Build**: Maven
- **Testing**: JUnit 5 + Mockito

### Frontend
- **Framework**: Vue.js 3.5.18 (Composition API)
- **Build Tool**: Vite 7.0.6
- **Styling**: Custom CSS + Bootstrap concepts
- **HTTP Client**: Axios
- **Routing**: Vue Router 4
- **State Management**: Composables pattern

### DevOps & Deployment
- **Containerization**: Docker + Docker Compose
- **Web Server**: Nginx (for frontend)
- **Database**: PostgreSQL container
- **Networking**: Docker bridge network
- **Health Checks**: Application and database monitoring

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

## 🚢 Deployment & Management

### Docker Commands
```bash
# Start all services
docker-compose up -d

# View service status
docker-compose ps

# Stop all services
docker-compose down

# Rebuild and restart
docker-compose up --build -d

# View logs
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f postgres
```

### Database Management
```bash
# Connect to database
docker exec -it plas-postgres psql -U postgres -d plas

# Backup database
docker exec plas-postgres pg_dump -U postgres plas > backup.sql

# Restore database
docker exec -i plas-postgres psql -U postgres plas < backup.sql

# View tables
docker exec plas-postgres psql -U postgres -d plas -c "\dt"
```

### Application URLs
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080
- **Database**: localhost:5432 (postgres/12@Batworld)

### Default Test Users
```
Admin User:
- Email: admin@plas.com
- Password: admin123

Customer User:
- Email: customer@plas.com  
- Password: customer123
```

## 🧪 Testing

### Backend Testing
```bash
# Run unit tests
cd backend
mvn test

# Run integration tests
mvn verify

# Generate test coverage report
mvn jacoco:report
```

### Frontend Testing
```bash
# Run unit tests
cd frontend
npm run test

# Run end-to-end tests
npm run test:e2e

# Generate coverage report
npm run test:coverage
```

### API Testing Examples
```bash
# Test authentication
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"identifier":"customer@plas.com","password":"customer123"}'

# Test loan application
curl -X POST http://localhost:8080/api/loans/apply \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{"userId":"1","amount":500000,"tenureMonths":24,"income":50000,"creditScore":750,"purpose":"Home renovation"}'

# Test EMI calculation
curl -X POST http://localhost:8080/api/emi/preview \
  -H "Content-Type: application/json" \
  -d '{"amount":500000,"interestRate":8.5,"tenureMonths":24}'
```

## 🔧 Development Setup

### Local Development (without Docker)

#### Backend Setup
```bash
# Navigate to backend directory
cd backend

# Install dependencies
mvn clean install

# Start PostgreSQL locally
# Update application.properties with local DB config

# Run application
mvn spring-boot:run
```

#### Frontend Setup
```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build
```

### Environment Configuration
```bash
# Backend environment variables
SPRING_PROFILES_ACTIVE=dev
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/plas
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=password

# Frontend environment variables
VITE_API_BASE_URL=http://localhost:8080/api
VITE_APP_TITLE=Personal Loan Application System
```

## 📈 Performance & Monitoring

### Health Checks
- **Backend Health**: http://localhost:8080/actuator/health
- **Database Status**: `docker exec plas-postgres pg_isready -U postgres`
- **Frontend Availability**: http://localhost:3000

### Monitoring Commands
```bash
# Monitor container resources
docker stats

# Check container health
docker inspect --format='{{.State.Health.Status}}' plas-backend

# View application metrics
curl http://localhost:8080/actuator/metrics
```

## 🚨 Troubleshooting

### Common Issues

1. **Port Conflicts**
   ```bash
   # Check port usage
   netstat -tulpn | grep :3000
   lsof -i :8080
   ```

2. **Database Connection Issues**
   ```bash
   # Check database logs
   docker-compose logs postgres
   
   # Verify database connectivity
   docker exec plas-postgres pg_isready -U postgres
   ```

3. **Build Failures**
   ```bash
   # Clean Docker cache
   docker system prune -a
   
   # Rebuild from scratch
   docker-compose build --no-cache
   ```

4. **Authentication Issues**
   - Verify JWT token in browser localStorage
   - Check CORS configuration in backend
   - Ensure proper role assignments

## 📚 Documentation

- **[Docker Deployment Guide](DOCKER_DEPLOYMENT.md)** - Comprehensive deployment instructions
- **API Documentation** - Available at http://localhost:8080/swagger-ui.html (when enabled)
- **Database Schema** - ERD and relationship documentation
- **Business Rules** - Complete business logic documentation

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Vue.js community for the reactive frontend framework
- PostgreSQL for the robust database system
- Docker for containerization technology

## 📞 Support

For support and queries:
- Create an issue in the repository
- Email: support@plas.com
- Documentation: Check the DOCKER_DEPLOYMENT.md file

---

**Personal Loan Application System (PLAS)** - Streamlining loan management with modern technology.