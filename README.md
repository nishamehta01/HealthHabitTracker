# Health Habit Tracker 🌿

A console-based Java application to track daily habits and analyze consistency over time.
Started as an OOP-based design project and later extended with a MySQL database and basic analytics.

---

## 🚀 Features

* Track daily habits like water, sleep, exercise, screen time, and reading
* Store data in MySQL for persistence
* View daily logs and habit progress
* Basic analytics like streak tracking and weekly performance

---

## 🧱 Project Structure

### Core Logic (Java)

* Designed using OOP principles with abstraction, interfaces, and enums
* Each habit has its own logic for completion
* Console-based menu for interaction

---

### Database Integration

* MySQL used to store habits and daily logs
* Structured tables with proper relationships

---

### Data Handling

* DAO layer handles all database operations
* Supports fetching habits, saving logs, and viewing daily entries

---

## 🛠 Tech Stack

* Java
* MySQL
* JDBC
* IntelliJ IDEA

---

## ▶️ How to run

1. Clone the repository
2. Create MySQL database:

   ```
   healthhabittracker
   ```
3. Add MySQL Connector/J `.jar` to `libs` folder
4. Update database credentials
5. Run `Main.java`

---

## 🔜 Next

* Build a frontend dashboard using HTML, CSS, and JavaScript
* Add charts for weekly scores and habit trends (Chart.js)
* Replace console input with interactive UI (sliders, toggles)
* Show pattern-based insights visually
* Later migrate to React for a cleaner UI

---
