package com.habittracker;

import java.sql.*;

public class StreakCalculator {

    private Connection connection;

    public StreakCalculator() {
        this.connection = DatabaseConnection.getConnection();
    }

    public int getStreak(int habitId) {
        int streak = 0;
        String query = "SELECT log_date, is_completed FROM daily_logs " +
                "WHERE habit_id = ? ORDER BY log_date DESC";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, habitId);
            ResultSet rs = pstmt.executeQuery();

            java.time.LocalDate expectedDate = java.time.LocalDate.now();

            while (rs.next()) {
                java.time.LocalDate logDate = rs.getDate("log_date").toLocalDate();
                boolean isCompleted = rs.getBoolean("is_completed");

                if (logDate.equals(expectedDate) && isCompleted) {
                    streak++;
                    expectedDate = expectedDate.minusDays(1);
                } else {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return streak;
    }

    public void showAllStreaks() {
        String query = "SELECT habit_id, habit_name FROM habits";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("\n--- Current Streaks ---");
            while (rs.next()) {
                int habitId = rs.getInt("habit_id");
                String habitName = rs.getString("habit_name");
                int streak = getStreak(habitId);
                System.out.println(habitName + " — " + streak + " day streak");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
