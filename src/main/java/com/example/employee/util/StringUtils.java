package com.example.employee.util;

public class StringUtils {
    
    // Private constructor to prevent instantiation
    private StringUtils() {
    }
    
    // GOTCHA #11: String concatenation instead of Text Blocks (Java 15+)
    public static String generateReport(String title, String content, String footer) {
        String report = "========================================\n";
        report += "         " + title + "\n";
        report += "========================================\n";
        report += "\n";
        report += content;
        report += "\n";
        report += "----------------------------------------\n";
        report += footer + "\n";
        report += "========================================\n";
        return report;
    }
    
    public static String maskSensitiveData(String data) {
        if (data == null || data.length() < 4) {
            return "****";
        }
        return "****" + data.substring(data.length() - 4);
    }
}

// Made with Bob
