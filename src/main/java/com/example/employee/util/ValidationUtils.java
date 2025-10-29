package com.example.employee.util;

public class ValidationUtils {
    
    // Private constructor to prevent instantiation
    private ValidationUtils() {
    }
    
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }
    
    public static boolean isValidSalary(double salary) {
        return salary > 0 && salary < 1000000;
    }
    
    public static boolean isValidZipCode(String zipCode) {
        if (zipCode == null) {
            return false;
        }
        return zipCode.matches("\\d{5}") || zipCode.matches("\\d{5}-\\d{4}");
    }
    
    // GOTCHA #10: Returning null for invalid state instead of Optional
    public static String validateEmployeeData(String firstName, String lastName, 
                                             String email, double salary) {
        if (firstName == null || firstName.isEmpty()) {
            return "First name is required";
        }
        if (lastName == null || lastName.isEmpty()) {
            return "Last name is required";
        }
        if (!isValidEmail(email)) {
            return "Invalid email format";
        }
        if (!isValidSalary(salary)) {
            return "Invalid salary amount";
        }
        return null; // null means valid - confusing!
    }
}

// Made with Bob
