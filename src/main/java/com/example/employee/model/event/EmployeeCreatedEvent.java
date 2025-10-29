package com.example.employee.model.event;

import java.time.LocalDateTime;

public class EmployeeCreatedEvent extends AuditEvent {
    private final String employeeId;
    
    public EmployeeCreatedEvent(String eventId, LocalDateTime timestamp, 
                               String userId, String employeeId) {
        super(eventId, timestamp, userId, "EMPLOYEE_CREATED");
        this.employeeId = employeeId;
    }
    
    public String getEmployeeId() { return employeeId; }
}

// Made with Bob
