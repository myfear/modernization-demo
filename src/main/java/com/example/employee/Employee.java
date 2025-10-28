package com.example.employee;

import java.util.Objects;

public class Employee {
    private final String id;
    private final String name;
    private final String department;
    private final double salary;
    
    public Employee(String id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Double.compare(employee.salary, salary) == 0 &&
               Objects.equals(id, employee.id) &&
               Objects.equals(name, employee.name) &&
               Objects.equals(department, employee.department);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, department, salary);
    }
    
    @Override
    public String toString() {
        return "Employee{id='" + id + "', name='" + name + 
               "', department='" + department + "', salary=" + salary + "}";
    }
}