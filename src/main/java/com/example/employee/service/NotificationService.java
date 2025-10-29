package com.example.employee.service;

import com.example.employee.model.Employee;
import java.util.List;

// GOTCHA #23: Synchronous I/O-bound operations (Virtual Threads in Java 21 would help)
public class NotificationService {
    
    public void sendPayrollNotifications(List<Employee> employees) {
        System.out.println("Sending notifications to " + employees.size() + " employees...");
        
        for (Employee emp : employees) {
            sendEmail(emp.getEmail(), "Payroll Processed", 
                     "Your payroll for this month has been processed.");
            // Simulate slow I/O operation
            try {
                Thread.sleep(100); // Each email takes 100ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("All notifications sent!");
    }
    
    private void sendEmail(String to, String subject, String body) {
        System.out.println("  Sending email to: " + to);
    }
}

// Made with Bob
