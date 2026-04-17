package com.habittracker;

import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

public class ReportGenerator {

    private Connection connection;
    private AnalyticsEngine analytics;
    private StreakCalculator streakCalc;

    public ReportGenerator() {
        this.connection = DatabaseConnection.getConnection();
        this.analytics = new AnalyticsEngine();
        this.streakCalc = new StreakCalculator();
    }

    public void generateMonthlyReport() {
        String month = LocalDate.now().getMonth().toString();
        int year = LocalDate.now().getYear();
        StringBuilder report = new StringBuilder();

        report.append("====================================\n");
        report.append("  HEALTH HABIT TRACKER — MONTHLY REPORT\n");
        report.append("  ").append(month).append(" ").append(year).append("\n");
        report.append("====================================\n\n");

        double weeklyScore = analytics.getWeeklyScore();
        report.append("Weekly Health Score: ").append(weeklyScore).append("%\n");

        String weakDay = analytics.getWeakDay();
        report.append("Weakest Day: ").append(weakDay).append("\n");

        String pattern = analytics.detectPattern();
        report.append("Pattern Detected: ").append(pattern).append("\n\n");

        report.append("--- Habit Streaks ---\n");
        String query = "SELECT habit_id, habit_name FROM habits";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("habit_id");
                String name = rs.getString("habit_name");
                int streak = streakCalc.getStreak(id);
                report.append(name).append(": ").append(streak).append(" day streak\n");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        String fileName = "Report_" + month + "_" + year + ".txt";
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(report.toString());
            writer.close();
            System.out.println("\nReport saved: " + fileName);
            System.out.println(report);
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }
}
