package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

public class ReportingService {
    private final EmployeeRepository repository;
    private final PayrollService payrollService;
    
    public ReportingService(EmployeeRepository repository, PayrollService payrollService) {
        this.repository = repository;
        this.payrollService = payrollService;
    }
    
    // GOTCHA #21: Manual calculation instead of Collectors
    public String generateDepartmentReport(String department) {
        List<Employee> deptEmployees = repository.findByDepartment(department);
        
        if (deptEmployees.isEmpty()) {
            return "No employees in department: " + department;
        }
        
        double totalSalary = 0;
        double minSalary = Double.MAX_VALUE;
        double maxSalary = Double.MIN_VALUE;
        
        for (Employee emp : deptEmployees) {
            double salary = emp.getSalary();
            totalSalary += salary;
            if (salary < minSalary) {
                minSalary = salary;
            }
            if (salary > maxSalary) {
                maxSalary = salary;
            }
        }
        
        double avgSalary = totalSalary / deptEmployees.size();
        
        String report = "===========================================\n";
        report += "      DEPARTMENT REPORT: " + department + "\n";
        report += "===========================================\n";
        report += "Total Employees:   " + deptEmployees.size() + "\n";
        report += "Total Salary Cost: $" + String.format("%.2f", totalSalary) + "\n";
        report += "Average Salary:    $" + String.format("%.2f", avgSalary) + "\n";
        report += "Minimum Salary:    $" + String.format("%.2f", minSalary) + "\n";
        report += "Maximum Salary:    $" + String.format("%.2f", maxSalary) + "\n";
        report += "===========================================\n";
        
        return report;
    }
    
    // GOTCHA #22: Imperative style instead of functional/declarative
    public List<String> getEmployeeSummaries() {
        List<Employee> employees = repository.findAll();
        List<String> summaries = new ArrayList<>();
        
        for (Employee emp : employees) {
            String summary = emp.getId() + ": " + 
                           emp.getFullName() + " - " + 
                           emp.getDepartment() + " - $" + 
                           String.format("%.0f", emp.getSalary());
            summaries.add(summary);
        }
        
        return summaries;
    }
}

// Made with Bob
