package com.example.employee.exception;

public class EmployeeNotFoundException extends Exception {
    private final String employeeId;
    
    public EmployeeNotFoundException(String employeeId) {
        super("Employee not found with ID: " + employeeId);
        this.employeeId = employeeId;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
}

// Made with Bob
