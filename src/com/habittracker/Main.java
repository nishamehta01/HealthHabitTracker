package com.habittracker;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        HabitDAO habitDAO = new HabitDAO();
        StreakCalculator streakCalc = new StreakCalculator();
        AnalyticsEngine analytics = new AnalyticsEngine();
        ReportGenerator reportGen = new ReportGenerator();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Health Habit Tracker =====");
            System.out.println("1. View all habits");
            System.out.println("2. Log today's entry");
            System.out.println("3. View today's logs");
            System.out.println("4. View streaks");
            System.out.println("5. View analytics");
            System.out.println("6. Generate monthly report");
            System.out.println("7. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    ArrayList<String> habits = habitDAO.getAllHabits();
                    System.out.println("\n--- Your Habits ---");
                    for (String habit : habits) {
                        System.out.println(habit);
                    }
                    break;

                case 2:
                    System.out.print("Enter Habit ID: ");
                    int habitId = scanner.nextInt();
                    System.out.print("Enter actual value: ");
                    double actualValue = scanner.nextDouble();
                    System.out.print("Completed? (true/false): ");
                    boolean completed = scanner.nextBoolean();
                    habitDAO.saveLog(habitId, actualValue, completed);
                    break;

                case 3:
                    habitDAO.getTodayLogs();
                    break;

                case 4:
                    streakCalc.showAllStreaks();
                    break;

                case 5:
                    analytics.showTodayScore();
                    System.out.println("\nWeekly Score: " + analytics.getWeeklyScore() + "%");
                    System.out.println("Weak Day: " + analytics.getWeakDay());
                    System.out.println("Pattern: " + analytics.detectPattern());
                    break;

                case 6:
                    reportGen.generateMonthlyReport();
                    break;

                case 7:
                    DatabaseConnection.closeConnection();
                    System.out.println("Goodbye! Stay healthy!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}