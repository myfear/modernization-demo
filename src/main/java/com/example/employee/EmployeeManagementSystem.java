package com.example.employee;

import java.util.*;
import java.util.stream.*;

public class EmployeeManagementSystem {
    private List<Employee> employees;
    
    public EmployeeManagementSystem() {
        this.employees = new ArrayList<>();
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        employees.add(new Employee("E001", "Alice Johnson", "Engineering", 95000));
        employees.add(new Employee("E002", "Bob Smith", "Sales", 75000));
        employees.add(new Employee("E003", "Charlie Brown", "Engineering", 85000));
        employees.add(new Employee("E004", "Diana Prince", "HR", 70000));
        employees.add(new Employee("E005", "Eve Davis", "Sales", 80000));
    }
    
    public String getDepartmentBudget(String department) {
        String budget;
        switch (department) {
            case "Engineering":
                budget = "$500,000";
                break;
            case "Sales":
                budget = "$300,000";
                break;
            case "HR":
                budget = "$150,000";
                break;
            default:
                budget = "Unknown";
                break;
        }
        return budget;
    }
    
    public String processValue(Object value) {
        if (value instanceof String) {
            String str = (String) value;
            return "String: " + str.toUpperCase();
        } else if (value instanceof Integer) {
            Integer num = (Integer) value;
            return "Number: " + (num * 2);
        } else if (value instanceof Employee) {
            Employee emp = (Employee) value;
            return "Employee: " + emp.getName();
        } else {
            return "Unknown type";
        }
    }
    
    public String getEmployeeDepartment(String employeeId) {
        Employee emp = findEmployeeById(employeeId);
        if (emp != null) {
            String dept = emp.getDepartment();
            if (dept != null) {
                return dept;
            } else {
                return "No department";
            }
        } else {
            return "Employee not found";
        }
    }
    
    private Employee findEmployeeById(String id) {
        return employees.stream()
            .filter(e -> e.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    public String generateReport() {
        String report = "=== Employee Report ===\n";
        report += "Total Employees: " + employees.size() + "\n";
        report += "Departments: " + getDepartments() + "\n";
        report += "Average Salary: " + calculateAverageSalary() + "\n";
        report += "======================\n";
        return report;
    }
    
    public String generateEmailTemplate(String employeeName, double bonus) {
        return "Dear " + employeeName + ",\n\n" +
               "We are pleased to inform you that you have been awarded a bonus of " +
               EmployeeUtils.formatSalary(bonus) + ".\n\n" +
               "This bonus reflects your outstanding contribution to the company.\n\n" +
               "Best regards,\n" +
               "HR Department";
    }
    
    public List<String> getEmployeeNames() {
        return Collections.unmodifiableList(
            employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList())
        );
    }
    
    public List<Employee> getHighEarnersUpToLimit(double threshold, int limit) {
        List<Employee> result = new ArrayList<>();
        int count = 0;
        for (Employee emp : employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .collect(Collectors.toList())) {
            if (count >= limit) break;
            if (emp.getSalary() >= threshold) {
                result.add(emp);
                count++;
            }
        }
        return result;
    }
    
    public Employee getRandomEmployee() {
        Random random = new Random();
        int index = random.nextInt(employees.size());
        return employees.get(index);
    }
    
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
    
    private Set<String> getDepartments() {
        return employees.stream()
            .map(Employee::getDepartment)
            .collect(Collectors.toSet());
    }
    
    private double calculateAverageSalary() {
        return employees.stream()
            .mapToDouble(Employee::getSalary)
            .average()
            .orElse(0.0);
    }
    
    public static void main(String[] args) {
        EmployeeManagementSystem system = new EmployeeManagementSystem();
        
        System.out.println("1. Department Budget:");
        System.out.println(system.getDepartmentBudget("Engineering"));
        
        System.out.println("\n2. Process Values:");
        System.out.println(system.processValue("hello"));
        System.out.println(system.processValue(42));
        
        System.out.println("\n3. Employee Department:");
        System.out.println(system.getEmployeeDepartment("E001"));
        
        System.out.println("\n4. Report:");
        System.out.println(system.generateReport());
        
        System.out.println("5. Email Template:");
        System.out.println(system.generateEmailTemplate("Alice Johnson", 5000));
        
        System.out.println("\n6. Employee Names:");
        System.out.println(system.getEmployeeNames());
        
        System.out.println("\n7. High Earners:");
        System.out.println(system.getHighEarnersUpToLimit(80000, 2));
        
        System.out.println("\n8. Random Employee:");
        System.out.println(system.getRandomEmployee());
    }
}