package com.habittracker;

import java.sql.*;
import java.util.ArrayList;

public class HabitDAO {

    private Connection connection;

    public HabitDAO() {
        this.connection = DatabaseConnection.getConnection();
    }


    public ArrayList<String> getAllHabits() {
        ArrayList<String> habits = new ArrayList<>();
        String query = "SELECT * FROM habits";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String habit = rs.getInt("habit_id") + ". " +
                        rs.getString("habit_name") +
                        " | Target: " + rs.getDouble("target_value") +
                        " " + rs.getString("unit");
                habits.add(habit);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return habits;
    }

    public void saveLog(int habitId, double actualValue, boolean isCompleted) {
        String query = "INSERT INTO daily_logs (habit_id, log_date, actual_value, is_completed) VALUES (?, CURDATE(), ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, habitId);
            pstmt.setDouble(2, actualValue);
            pstmt.setBoolean(3, isCompleted);
            pstmt.executeUpdate();
            System.out.println("Log saved successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getTodayLogs() {
        String query = "SELECT h.habit_name, dl.actual_value, dl.is_completed " +
                "FROM daily_logs dl JOIN habits h ON dl.habit_id = h.habit_id " +
                "WHERE dl.log_date = CURDATE()";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("\n--- Today's Logs ---");
            while (rs.next()) {
                System.out.println(rs.getString("habit_name") +
                        " | Actual: " + rs.getDouble("actual_value") +
                        " | Completed: " + rs.getBoolean("is_completed"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
