# 📚 E-Learning Content Organizer

A Java + Oracle DB based desktop application for managing online learning content.

# 🚀 Overview

The E-Learning Content Organizer is a desktop-based learning management system built using Java (AWT GUI) and Oracle Database.
It is designed for three types of users:

Admin → manages users & courses

Teacher → uploads lecture videos & study materials

Student → views courses & accesses study resources

The system focuses on simplicity, fast performance, and structured learning content access.

## 🛠️ Tech Stack

- **Frontend (Desktop UI)**
  - Java AWT (Frame, Label, Button, TextField, TextArea)
  - Event Handling (ActionListener, WindowAdapter)
  - Dialog-based popups
  - Dark theme UI styling

- **Backend**
  - Java OOP
    - Encapsulation
    - Inheritance
    - Polymorphism
    - Abstraction (DAO pattern)
  - Exception Handling
  - Modular Code Structure (UI + DAO + Models)

- **Database**
  - Oracle Database 11g XE
  - SQL (DDL, DML)
  - Constraints
    - Primary Key
    - Foreign Key
    - Unique
  - JDBC Connectivity (ojdbc8.jar)


## 📌 Features

- **Admin Module**
  - Add new users (Admin/Teacher/Student)
  - List existing users
  - Delete users
  - Manage courses

- **Teacher Module**
  - Create courses
  - Upload lecture videos (YouTube links or file paths)
  - Upload study materials (PDF, PPT, DOC, video links)
  - Update/delete materials
  - View uploaded material list

- **Student Module**
  - View available courses
  - Watch lecture videos
  - Open study materials
  - Download material files
  - Clean and simple UI for learning

- **System Features**
  - Role-based UI redirection
  - DAO pattern for database operations
  - JDBC with PreparedStatements for secure queries
  - Organized folder structure (ui, dao, models, db)


# 🧩 System Architecture
- elearn/
 - ├── ui/         → Java AWT Screens (LoginUI, AdminUI, StudentUI, TeacherUI…)
 - ├── dao/        → Database interaction classes (UserDAO, CourseDAO…)
 - ├── models/     → POJO classes (User, Course, Material)
 - ├── db/         → DBConnection.java for Oracle connectivity
 - └── Main.java   → Application entry point


The project follows clear separation between:

UI Layer

Business Logic Layer

# Database Layer (DAO)

- **🗄️ Database Schema**
- Users Table
- ID (PK)
- USERNAME (UNIQUE)
- PASSWORD
- ROLE (ADMIN / TEACHER / STUDENT)

- **Courses Table**
- ID (PK)
- TITLE
- DESCRIPTION
- INSTRUCTOR
- SOURCE   -- lecture video link

- Materials Table
- ID (PK)
- COURSEID (FK → courses.id)
- TITLE
- DESCRIPTION
- FILEPATH

# 🔌 JDBC Connectivity

The project uses ojdbc8.jar for Oracle connection:

Class.forName("oracle.jdbc.driver.OracleDriver");
Connection conn = DriverManager.getConnection(URL, USER, PASS);

# 🧪 Core Functionalities Demonstrated

- ✔ Database CRUD Operations
- ✔ DAO Pattern
- ✔ Java GUI Development (AWT)
- ✔ Exception Handling
- ✔ JDBC Prepared Statements
- ✔ Dynamic Polymorphism (Role-based UI loading)
- ✔ Modular Application Design
- ✔ Handling external file paths & URLs

## 📺 Screens Included

- **Login & Navigation**
  - Login Screen  
  - Role-based redirection (Admin / Teacher / Student)

- **Admin Screens**
  - Admin Dashboard  
  - User Management UI  
    - Add User  
    - List Users  
    - Delete User  
  - Course Management UI  
    - Add Course  
    - List Courses  
    - Delete Course  

- **Teacher Screens**
  - Teacher Dashboard  
  - Upload Material UI  
    - Upload PDF / DOC / PPT / Video Link  
    - Add Material Description  
  - Teacher Material Manager  
    - Update Material  
    - Delete Material  
  - Teacher Course Management  

- **Student Screens**
  - Student Dashboard  
  - Student Courses UI  
    - List all courses  
  - Student Course Detail UI  
    - View lecture video  
    - Open material  
    - Download material  


# 📦 How to Run

Install Oracle Database 11g XE

Create required tables (SQL script included in repository)

Add ojdbc8.jar to Eclipse project build path

Run Main.java

Login using default credentials (if any provided)

# 🎯 Learning Outcomes

This project showcases knowledge in:

Java

OOP Concepts (Encapsulation, Inheritance, Polymorphism, Abstraction)

AWT GUI programming

Event-driven programming

File handling

JDBC integration

DBMS

SQL CRUD operations

Schema design

Constraints (PK, FK, UNIQUE)

Normalization

Oracle SQL & JDBC connectivity

# 🤝 Contribution

Pull requests are welcome!
Improve UI, add authentication hashing, or enhance file upload support.

# 📜 License

This project is open-source under the MIT License.