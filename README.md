# Student Grade Tracker

A robust and interactive desktop application built with Java and Swing to track, manage, and analyze student grades across different classrooms.

Developed as part of the **CodeAlpha Java Programming Internship** (Task 1).

---

## 🚀 Features

- **Classroom Management:** Create and switch between multiple classrooms/sections dynamically.
- **Full CRUD Operations:** Add, update, delete, and view students along with their grades.
- **Statistical Analysis:** Real-time computation of:
    - Class Average
    - Highest Scoring Student
    - Lowest Scoring Student
    - Pass/Fail Status (threshold: 50)
- **Input Validation & Exception Handling:** Prevents invalid string inputs and out-of-range scores (0 - 100).
- **Modern Desktop GUI:** Built using pure Java Swing with system native look-and-feel.

---

## 🛠️ Tech Stack & Concepts

- **Language:** Java (JDK 17+)
- **GUI Framework:** Java Swing (`JFrame`, `JTable`, `DefaultTableModel`, `JOptionPane`)
- **Core Concepts:**
    - Object-Oriented Programming (OOP) — Encapsulation, Abstraction, Polymorphism
    - Data Structures — `ArrayList`, Dynamic Collection Management
    - Concurrency & Threading — Event Dispatch Thread (`SwingUtilities.invokeLater`)
    - Exception Handling — Robust input validation against `NumberFormatException`

---

## 📦 How to Run

1. Clone the repository:
   ```bash
   git clone [https://github.com/](https://github.com/)<your-username>/CodeAlpha_StudentGradeTracker.git