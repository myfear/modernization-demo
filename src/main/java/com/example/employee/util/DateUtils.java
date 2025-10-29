package com.example.employee.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    // Private constructor to prevent instantiation
    private DateUtils() {
    }
    
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "N/A";
        }
        return date.format(FORMATTER);
    }
    
    public static int calculateYearsOfService(LocalDate hireDate) {
        if (hireDate == null) {
            return 0;
        }
        return Period.between(hireDate, LocalDate.now()).getYears();
    }
    
    public static boolean isEligibleForBonus(LocalDate hireDate) {
        return calculateYearsOfService(hireDate) >= 1;
    }
    
    public static LocalDate getNextReviewDate(LocalDate hireDate) {
        if (hireDate == null) {
            return null;
        }
        return hireDate.plusYears(1);
    }
}

// Made with Bob
