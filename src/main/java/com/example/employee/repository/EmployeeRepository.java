package com.example.employee.repository;

import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.model.Address;
import com.example.employee.model.Employee;
import com.example.employee.model.PaymentInfo;
import com.example.employee.model.PerformanceReview;
import com.example.employee.model.enums.EmploymentType;
import com.example.employee.model.enums.PaymentMethod;
import com.example.employee.model.enums.ReviewType;
import com.example.employee.model.event.AuditEvent;
import com.example.employee.model.event.EmployeeCreatedEvent;
import com.example.employee.util.IdGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeRepository {
    private final Map<String, Employee> employees;
    private final Map<String, List<PerformanceReview>> reviews;
    private final List<AuditEvent> auditLog;
    
    public EmployeeRepository() {
        this.employees = new HashMap<>();
        this.reviews = new HashMap<>();
        this.auditLog = new ArrayList<>();
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Create sample employees
        List<String> engineeringSkills = Arrays.asList("Java", "Python", "AWS", "Docker");
        List<String> salesSkills = Arrays.asList("Salesforce", "Negotiation", "CRM");
        List<String> hrSkills = Arrays.asList("Recruiting", "Employee Relations", "Training");
        
        Address addr1 = new Address("123 Main St", "San Francisco", "CA", "94102", "USA");
        PaymentInfo payment1 = new PaymentInfo("1234567890", "987654321", 
                                              "Chase Bank", PaymentMethod.DIRECT_DEPOSIT);
        Employee emp1 = new Employee("E001", "Alice", "Johnson", "alice@company.com",
            "Engineering", "Senior Engineer", 120000, LocalDate.of(2020, 1, 15), 
            addr1, payment1, EmploymentType.FULL_TIME, engineeringSkills);
        
        Address addr2 = new Address("456 Oak Ave", "New York", "NY", "10001", "USA");
        PaymentInfo payment2 = new PaymentInfo("2345678901", "876543219", 
                                              "Bank of America", PaymentMethod.DIRECT_DEPOSIT);
        Employee emp2 = new Employee("E002", "Bob", "Smith", "bob@company.com",
            "Sales", "Sales Manager", 95000, LocalDate.of(2019, 6, 1), 
            addr2, payment2, EmploymentType.FULL_TIME, salesSkills);
        
        Address addr3 = new Address("789 Pine Rd", "Austin", "TX", "73301", "USA");
        PaymentInfo payment3 = new PaymentInfo("3456789012", "765432198", 
                                              "Wells Fargo", PaymentMethod.CHECK);
        Employee emp3 = new Employee("E003", "Charlie", "Brown", "charlie@company.com",
            "Engineering", "Lead Engineer", 140000, LocalDate.of(2018, 3, 20), 
            addr3, payment3, EmploymentType.FULL_TIME, engineeringSkills);
        
        Address addr4 = new Address("321 Elm St", "Seattle", "WA", "98101", "USA");
        PaymentInfo payment4 = new PaymentInfo("4567890123", "654321987", 
                                              "US Bank", PaymentMethod.DIRECT_DEPOSIT);
        Employee emp4 = new Employee("E004", "Diana", "Prince", "diana@company.com",
            "HR", "HR Director", 110000, LocalDate.of(2021, 9, 10), 
            addr4, payment4, EmploymentType.FULL_TIME, hrSkills);
        
        employees.put(emp1.getId(), emp1);
        employees.put(emp2.getId(), emp2);
        employees.put(emp3.getId(), emp3);
        employees.put(emp4.getId(), emp4);
        
        // Add performance reviews
        reviews.put("E001", Arrays.asList(
            new PerformanceReview("E001", LocalDate.of(2023, 12, 15), 5, 
                                 "Excellent work on new features", 0.10, ReviewType.ANNUAL),
            new PerformanceReview("E001", LocalDate.of(2024, 12, 15), 4, 
                                 "Good performance overall", 0.08, ReviewType.ANNUAL)
        ));
        
        reviews.put("E003", Arrays.asList(
            new PerformanceReview("E003", LocalDate.of(2023, 12, 15), 5, 
                                 "Outstanding leadership", 0.12, ReviewType.ANNUAL)
        ));
    }
    
    public Employee findById(String id) throws EmployeeNotFoundException {
        Employee emp = employees.get(id);
        if (emp == null) {
            throw new EmployeeNotFoundException(id);
        }
        return emp;
    }
    
    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }
    
    // Using Collectors.toList() for compatibility
    public List<Employee> findByDepartment(String department) {
        return employees.values().stream()
            .filter(e -> e.getDepartment().equals(department))
            .collect(Collectors.toList());
    }
    
    public List<Employee> findBySalaryRange(double minSalary, double maxSalary) {
        return employees.values().stream()
            .filter(e -> e.getSalary() >= minSalary && e.getSalary() <= maxSalary)
            .collect(Collectors.toList());
    }
    
    public List<PerformanceReview> getReviews(String employeeId) {
        return reviews.getOrDefault(employeeId, Collections.emptyList());
    }
    
    public void save(Employee employee) {
        employees.put(employee.getId(), employee);
        auditLog.add(new EmployeeCreatedEvent(
            IdGenerator.generateEventId(),
            LocalDateTime.now(),
            "system",
            employee.getId()
        ));
    }
    
    public void delete(String id) {
        employees.remove(id);
        reviews.remove(id);
    }
    
    // GOTCHA #13: No easy way to get first/last elements (before SequencedCollection in Java 21)
    public Employee getNewestEmployee() {
        List<Employee> sorted = findAll().stream()
            .sorted(Comparator.comparing(Employee::getHireDate).reversed())
            .collect(Collectors.toList());
        
        if (sorted.isEmpty()) {
            return null;
        }
        return sorted.get(0); // Using get(0) for compatibility
    }
    
    public Employee getOldestEmployee() {
        List<Employee> sorted = findAll().stream()
            .sorted(Comparator.comparing(Employee::getHireDate))
            .collect(Collectors.toList());
        
        if (sorted.isEmpty()) {
            return null;
        }
        return sorted.get(0);
    }
    
    public List<AuditEvent> getAuditLog() {
        return new ArrayList<>(auditLog);
    }
}

// Made with Bob
