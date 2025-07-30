# 🎓 # 🚀 CareerUdaan: A Smart Placement Prediction Console App (Java + JDBC + MySQL)


A console-based Java application that allows users to manage student placement records. It takes input from the user, stores student data in a MySQL database, and determines placement eligibility based on CGPA using JDBC.

---

## ✅ Features
- Capture student details via console input
- Store and manage data in a MySQL database
- Determine placement eligibility using CGPA
- Backend integration using JDBC

---

## 🛠 Technologies Used
- Java
- JDBC (Java Database Connectivity)
- MySQL

---

## 🚀 How to Run
1. Set up MySQL and create the required table.
2. Update database credentials in the Java code.
3. Compile and run the Java file from your IDE or terminal.

---

## 🗃️ MySQL Table Structure

```sql
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    roll_no VARCHAR(20),
    cgpa FLOAT,
    is_eligible BOOLEAN
);
