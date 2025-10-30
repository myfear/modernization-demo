package com.example.employee.service;

import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.model.Address;
import com.example.employee.model.Employee;
import com.example.employee.model.PerformanceReview;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.util.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService {
    private final EmployeeRepository repository;
    
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }
    
    // GOTCHA #14: Complex instanceof checks without pattern matching (Java 16+)
    public String processEmployeeData(Object data) {
        if (data instanceof Employee emp) {
            return "Employee: " + emp.getFullName() + " (" + emp.getDepartment() + ")";
        } else if (data instanceof PerformanceReview review) {
            return "Review for " + review.getEmployeeId() + ": Rating " + review.getRating();
        } else if (data instanceof String str) {
            if (str.startsWith("E")) {
                try {
                    Employee emp = repository.findById(str);
                    return "Found: " + emp.getFullName();
                } catch (EmployeeNotFoundException e) {
                    return "Not found: " + str;
                }
            }
            return "String data: " + str;
        } else if (data instanceof List<?> list) {
            return "List with " + list.size() + " items";
        } else if (data instanceof Map<?, ?> map) {
            return "Map with " + map.size() + " entries";
        } else {
            return "Unknown data type: " + (data != null ? data.getClass().getSimpleName() : "null");
        }
    }
    
    // GOTCHA #15: Old-style switch statement (Java 14+ has switch expressions)
    public String getEmployeeStatus(Employee emp) {
        int yearsOfService = DateUtils.calculateYearsOfService(emp.getHireDate());
        String status;
        
        switch (yearsOfService) {
            case 0:
                status = "New Hire";
                break;
            case 1:
                status = "Junior";
                break;
            case 2:
            case 3:
                status = "Intermediate";
                break;
            case 4:
            case 5:
                status = "Senior";
                break;
            default:
                if (yearsOfService > 10) {
                    status = "Veteran";
                } else {
                    status = "Experienced";
                }
                break;
        }
        return status;
    }
    
    // GOTCHA #16: Pyramid of doom with nested null checks (Optional would help)
    public String getEmployeeCity(String employeeId) {
        try {
            Employee emp = repository.findById(employeeId);
            if (emp != null) {
                Address addr = emp.getAddress();
                if (addr != null) {
                    String city = addr.getCity();
                    if (city != null && !city.isEmpty()) {
                        return city.toUpperCase();
                    } else {
                        return "UNKNOWN";
                    }
                } else {
                    return "NO ADDRESS";
                }
            } else {
                return "NOT FOUND";
            }
        } catch (EmployeeNotFoundException e) {
            return "ERROR: " + e.getMessage();
        }
    }
    
    // GOTCHA #17: Manual limiting instead of Stream.limit()
    public List<Employee> getTopPerformers(int limit) {
        List<Employee> allEmployees = repository.findAll();
        List<Employee> sorted = allEmployees.stream()
            .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
            .collect(Collectors.toList());
        
        List<Employee> result = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, sorted.size()); i++) {
            result.add(sorted.get(i));
        }
        return result;
    }
    
    public Map<String, Long> getDepartmentCounts() {
        return repository.findAll().stream()
            .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
    }
    
    public double getAverageSalaryByDepartment(String department) {
        return repository.findByDepartment(department).stream()
            .mapToDouble(Employee::getSalary)
            .average()
            .orElse(0.0);
    }
    
    // GOTCHA #18: Complex nested loops and conditionals
    public Map<String, List<Employee>> groupEmployeesBySkills() {
        Map<String, List<Employee>> skillMap = new HashMap<>();
        List<Employee> allEmployees = repository.findAll();
        
        for (Employee emp : allEmployees) {
            List<String> skills = emp.getSkills();
            for (String skill : skills) {
                if (!skillMap.containsKey(skill)) {
                    skillMap.put(skill, new ArrayList<>());
                }
                List<Employee> employeesWithSkill = skillMap.get(skill);
                if (!employeesWithSkill.contains(emp)) {
                    employeesWithSkill.add(emp);
                }
            }
        }
        
        return skillMap;
    }
}

// Made with Bob
