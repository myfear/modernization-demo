package com.example.employee.model.event;

import java.time.LocalDateTime;

public class SalaryChangedEvent extends AuditEvent {
    private final String employeeId;
    private final double oldSalary;
    private final double newSalary;
    
    public SalaryChangedEvent(String eventId, LocalDateTime timestamp, 
                             String userId, String employeeId,
                             double oldSalary, double newSalary) {
        super(eventId, timestamp, userId, "SALARY_CHANGED");
        this.employeeId = employeeId;
        this.oldSalary = oldSalary;
        this.newSalary = newSalary;
    }
    
    public String getEmployeeId() { return employeeId; }
    public double getOldSalary() { return oldSalary; }
    public double getNewSalary() { return newSalary; }
}

// Made with Bob
