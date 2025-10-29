package com.example.employee.exception;

public class ValidationException extends Exception {
    private final String field;
    private final String reason;
    
    public ValidationException(String field, String reason) {
        super("Validation failed for " + field + ": " + reason);
        this.field = field;
        this.reason = reason;
    }
    
    public String getField() { return field; }
    public String getReason() { return reason; }
}

// Made with Bob
