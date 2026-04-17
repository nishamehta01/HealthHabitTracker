package com.habittracker;

import java.sql.*;

public class AnalyticsEngine {

    private Connection connection;

    public AnalyticsEngine() {
        this.connection = DatabaseConnection.getConnection();
    }

    public double getWeeklyScore() {
        String query = "SELECT COUNT(*) as total, SUM(is_completed) as completed " +
                "FROM daily_logs " +
                "WHERE log_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int total = rs.getInt("total");
                int completed = rs.getInt("completed");
                if (total == 0) return 0;
                return Math.round((completed * 100.0 / total) * 10.0) / 10.0;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }

    public String getWeakDay() {
        String query = "SELECT DAYNAME(log_date) as day_name, " +
                "AVG(is_completed) as avg_completion " +
                "FROM daily_logs " +
                "GROUP BY DAYNAME(log_date) " +
                "ORDER BY avg_completion ASC LIMIT 1";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getString("day_name");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "Not enough data yet";
    }

    public String detectPattern() {
        String query = "SELECT " +
                "AVG(CASE WHEN s.actual_value >= 7 AND e.is_completed = true THEN 1 ELSE 0 END) as pattern " +
                "FROM daily_logs s " +
                "JOIN daily_logs e ON s.log_date = e.log_date " +
                "WHERE s.habit_id = 2 AND e.habit_id = 3";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                double pattern = rs.getDouble("pattern");
                if (pattern >= 0.7) {
                    return "When you sleep 7+ hours, you exercise " +
                            Math.round(pattern * 100) + "% of the time!";
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "Keep logging more days to detect patterns!";
    }

    public void showTodayScore() {
        String query = "SELECT COUNT(*) as total, SUM(is_completed) as completed " +
                "FROM daily_logs WHERE log_date = CURDATE()";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int total = rs.getInt("total");
                int completed = rs.getInt("completed");
                if (total == 0) {
                    System.out.println("No logs today yet!");
                    return;
                }
                double score = Math.round((completed * 100.0 / total) * 10.0) / 10.0;
                System.out.println("\n--- Today's Score ---");
                System.out.println("Completed: " + completed + "/" + total + " habits");
                System.out.println("Score: " + score + "%");
                if (score == 100) System.out.println("Perfect day! Amazing!");
                else if (score >= 60) System.out.println("Good job! Keep going!");
                else System.out.println("You can do better tomorrow!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
