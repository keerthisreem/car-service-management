# 🔧 Car Maintenance and Service Management System

A full-stack web application for managing car service appointments, vehicle records, and service history. Built with Spring Boot, Thymeleaf, Spring Security, JPA/Hibernate, and MySQL.

---

## 📸 Screenshots

### Login Page
> Clean dark-themed login with role-based access

### User Dashboard
> Overview of vehicles, appointments, and service status

### Admin Dashboard
> Manage all appointments, users, vehicles, and service types

---

## ✨ Features

### 👤 User
- Register & Login securely
- Add / remove vehicles
- Book service appointments
- Track appointment status (Pending → Approved → In Progress → Completed)
- View all available service types and costs

### 🛠️ Admin
- View all appointments and update status
- Approve, start, and complete service jobs
- Fill service completion form (work done, parts replaced, cost)
- Manage service types (add/delete)
- View all registered users and vehicles

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 3.2 |
| Frontend | Thymeleaf + HTML/CSS |
| Security | Spring Security |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL |
| Build Tool | Gradle |
| Language | Java 21 |

---

## 🗄️ Database Schema

```
users           → id, name, email, password, phone, role
vehicles        → id, make, model, year, registration_number, color, owner_id
service_types   → id, name, description, estimated_cost, estimated_duration_hours
appointments    → id, vehicle_id, service_type_id, appointment_date, notes, status
service_records → id, appointment_id, work_description, parts_replaced, total_cost, completed_date, mechanic_notes
```

---

## 🚀 Getting Started

### Prerequisites
- Java 21
- MySQL 8.0+
- IntelliJ IDEA

### Setup

1. **Clone the repository**
```bash
git clone https://github.com/keerthisreem/car-service-management.git
cd car-service-management/carservice
```

2. **Create MySQL database**
```sql
CREATE DATABASE carservice_db;
```

3. **Configure database** in `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/carservice_db
spring.datasource.username=root
spring.datasource.password=root
```

4. **Run the application**
```bash
./gradlew bootRun
```
Or open in IntelliJ and run `CarServiceApplication.java`

5. **Open in browser**
```
http://localhost:8080
```

---

## 🔑 Default Login Credentials

| Role | Email | Password |
|---|---|---|
| Admin | admin@carservice.com | admin123 |
| User | Register at /auth/register | your choice |

> ℹ️ Admin account and 7 service types are auto-created on first run.

---

## 📋 Workflow

```
User registers → Adds vehicle → Books appointment
                                      ↓
Admin: Pending → Approve → Start → Complete (fills service record)
                                      ↓
User sees status updated in real time
```

---

## 👩‍💻 Author

**Keerthisree M**
- Register No: 728824106074
- Department: Electronics & Communication Engineering
- College: Sri Eshwar College of Engineering

---

## 📄 License

This project is developed for academic purposes.
