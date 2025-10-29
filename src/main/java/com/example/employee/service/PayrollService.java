package com.example.employee.service;

import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.model.Employee;
import com.example.employee.model.PerformanceReview;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.util.DateUtils;
import com.example.employee.util.StringUtils;

import java.util.List;

public class PayrollService {
    private final EmployeeRepository repository;
    private static final double TAX_RATE = 0.25;
    private static final double HEALTH_INSURANCE = 500.0;
    private static final double RETIREMENT_PERCENT = 0.05;
    
    public PayrollService(EmployeeRepository repository) {
        this.repository = repository;
    }
    
    // GOTCHA #19: String concatenation instead of Text Blocks (Java 15+)
    public String generatePaystub(String employeeId) throws EmployeeNotFoundException {
        Employee emp = repository.findById(employeeId);
        
        double grossPay = emp.getSalary() / 12;
        double taxes = grossPay * TAX_RATE;
        double retirement = grossPay * RETIREMENT_PERCENT;
        double netPay = grossPay - taxes - HEALTH_INSURANCE - retirement;
        
        String payStub = "========================================\n";
        payStub += "           PAYROLL STATEMENT            \n";
        payStub += "========================================\n";
        payStub += "Employee: " + emp.getFullName() + "\n";
        payStub += "Employee ID: " + emp.getId() + "\n";
        payStub += "Department: " + emp.getDepartment() + "\n";
        payStub += "Position: " + emp.getPosition() + "\n";
        payStub += "Employment Type: " + emp.getEmploymentType() + "\n";
        payStub += "----------------------------------------\n";
        payStub += "Gross Pay:        $" + String.format("%.2f", grossPay) + "\n";
        payStub += "Taxes (25%):      $" + String.format("%.2f", taxes) + "\n";
        payStub += "Health Insurance: $" + String.format("%.2f", HEALTH_INSURANCE) + "\n";
        payStub += "Retirement (5%):  $" + String.format("%.2f", retirement) + "\n";
        payStub += "----------------------------------------\n";
        payStub += "Net Pay:          $" + String.format("%.2f", netPay) + "\n";
        payStub += "========================================\n";
        payStub += "Payment Method: " + emp.getPaymentInfo().getPaymentMethod() + "\n";
        payStub += "Bank: " + emp.getPaymentInfo().getBankName() + "\n";
        payStub += "Account: " + StringUtils.maskSensitiveData(
            emp.getPaymentInfo().getAccountNumber()) + "\n";
        payStub += "========================================\n";
        
        return payStub;
    }
    
    public double calculateBonus(String employeeId) throws EmployeeNotFoundException {
        Employee emp = repository.findById(employeeId);
        List<PerformanceReview> empReviews = repository.getReviews(employeeId);
        
        if (empReviews.isEmpty()) {
            return 0.0;
        }
        
        double avgBonusPercent = empReviews.stream()
            .mapToDouble(PerformanceReview::getBonusPercentage)
            .average()
            .orElse(0.0);
        
        return emp.getSalary() * avgBonusPercent;
    }
    
    // GOTCHA #20: Complex switch-like logic that could use switch expressions
    public double calculateAdjustedSalary(Employee emp, String adjustmentType) {
        double baseSalary = emp.getSalary();
        double adjustedSalary;
        
        if (adjustmentType.equals("COST_OF_LIVING")) {
            adjustedSalary = baseSalary * 1.03;
        } else if (adjustmentType.equals("MERIT")) {
            int years = DateUtils.calculateYearsOfService(emp.getHireDate());
            if (years >= 5) {
                adjustedSalary = baseSalary * 1.10;
            } else if (years >= 3) {
                adjustedSalary = baseSalary * 1.07;
            } else {
                adjustedSalary = baseSalary * 1.05;
            }
        } else if (adjustmentType.equals("PROMOTION")) {
            if (emp.getPosition().contains("Senior")) {
                adjustedSalary = baseSalary * 1.15;
            } else if (emp.getPosition().contains("Lead")) {
                adjustedSalary = baseSalary * 1.20;
            } else {
                adjustedSalary = baseSalary * 1.12;
            }
        } else {
            adjustedSalary = baseSalary;
        }
        
        return adjustedSalary;
    }
}

// Made with Bob
