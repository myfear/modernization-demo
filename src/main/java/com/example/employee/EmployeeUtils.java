package com.example.employee;

public interface EmployeeUtils {
    static String formatSalary(double salary) {
        return String.format("$%.2f", salary);
    }
}