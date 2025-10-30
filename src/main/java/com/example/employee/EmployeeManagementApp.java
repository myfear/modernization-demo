// ============================================
// ENHANCED JAVA 8 TO 21 MODERNIZATION DEMO
// Enterprise-Grade Application with 23+ Modernization Opportunities
// ============================================

package com.example.employee;

import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.NotificationService;
import com.example.employee.service.PayrollService;
import com.example.employee.service.ReportingService;
import com.example.employee.util.DateUtils;

import java.util.List;

public class EmployeeManagementApp {
    
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   Java 8 Enterprise Employee Management System");
        System.out.println("=================================================\n");
        
        EmployeeRepository repository = new EmployeeRepository();
        EmployeeService employeeService = new EmployeeService(repository);
        PayrollService payrollService = new PayrollService(repository);
        ReportingService reportingService = new ReportingService(repository, payrollService);
        NotificationService notificationService = new NotificationService();
        
        // Demo 1: Process different data types (instanceof gotcha)
        System.out.println("Demo 1: Processing various data types");
        System.out.println("  " + employeeService.processEmployeeData("E001"));
        System.out.println("  " + employeeService.processEmployeeData(repository.findAll()));
        
        // Demo 2: Employee status (switch gotcha)
        try {
            Employee emp = repository.findById("E001");
            System.out.println("\nDemo 2: Employee Status");
            System.out.println("  " + emp.getFullName() + " is: " + 
                             employeeService.getEmployeeStatus(emp));
        } catch (EmployeeNotFoundException e) {
            System.out.println("  Employee not found");
        }
        
        // Demo 3: Generate payroll (text blocks gotcha)
        try {
            System.out.println("\nDemo 3: Payroll Generation");
            System.out.println(payrollService.generatePaystub("E001"));
        } catch (EmployeeNotFoundException e) {
            System.out.println("  Employee not found");
        }
        
        // Demo 4: Department report
        System.out.println("\nDemo 4: Department Report");
        System.out.println(reportingService.generateDepartmentReport("Engineering"));
        
        // Demo 5: Top performers
        System.out.println("\nDemo 5: Top 3 Performers");
        List<Employee> topPerformers = employeeService.getTopPerformers(3);
        for (Employee emp : topPerformers) {
            System.out.println("  " + emp.getFullName() + ": $" +
                "%.0f".formatted(emp.getSalary()));
        }
        
        // Demo 6: Nested null checks gotcha
        System.out.println("\nDemo 6: Getting Employee City");
        System.out.println("  E001 city: " + employeeService.getEmployeeCity("E001"));
        
        // Demo 7: Sequenced collections gotcha
        System.out.println("\nDemo 7: Newest Employee");
        Employee newest = repository.getNewestEmployee();
        if (newest != null) {
            System.out.println("  " + newest.getFullName() + " (hired " + 
                             DateUtils.formatDate(newest.getHireDate()) + ")");
        }
        
        // Demo 8: Synchronous notifications (Virtual Threads would help)
        System.out.println("\nDemo 8: Sending Payroll Notifications");
        long startTime = System.currentTimeMillis();
        notificationService.sendPayrollNotifications(repository.findAll());
        long endTime = System.currentTimeMillis();
        System.out.println("  Time taken: " + (endTime - startTime) + "ms");
        
        System.out.println("\n=================================================");
        System.out.println("   Application Complete");
        System.out.println("   23+ Java 21 Modernization Opportunities Found!");
        System.out.println("=================================================");
    }
}

// Made with Bob
