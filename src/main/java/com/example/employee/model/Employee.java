package com.example.employee.model;

import com.example.employee.model.enums.EmploymentType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String department;
    private final String position;
    private final double salary;
    private final LocalDate hireDate;
    private final Address address;
    private final PaymentInfo paymentInfo;
    private final EmploymentType employmentType;
    private final List<String> skills;
    
    public Employee(String id, String firstName, String lastName, String email,
                   String department, String position, double salary, 
                   LocalDate hireDate, Address address, PaymentInfo paymentInfo,
                   EmploymentType employmentType, List<String> skills) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.address = address;
        this.paymentInfo = paymentInfo;
        this.employmentType = employmentType;
        this.skills = new ArrayList<>(skills);
    }
    
    // 12 getters...
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    public LocalDate getHireDate() { return hireDate; }
    public Address getAddress() { return address; }
    public PaymentInfo getPaymentInfo() { return paymentInfo; }
    public EmploymentType getEmploymentType() { return employmentType; }
    public List<String> getSkills() { return new ArrayList<>(skills); }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Double.compare(employee.salary, salary) == 0 &&
               Objects.equals(id, employee.id) &&
               Objects.equals(firstName, employee.firstName) &&
               Objects.equals(lastName, employee.lastName) &&
               Objects.equals(email, employee.email) &&
               Objects.equals(department, employee.department) &&
               Objects.equals(position, employee.position) &&
               Objects.equals(hireDate, employee.hireDate) &&
               Objects.equals(address, employee.address) &&
               Objects.equals(paymentInfo, employee.paymentInfo) &&
               Objects.equals(employmentType, employee.employmentType) &&
               Objects.equals(skills, employee.skills);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, department, 
                          position, salary, hireDate, address, paymentInfo,
                          employmentType, skills);
    }
    
    @Override
    public String toString() {
        return "Employee{" +
               "id='" + id + '\'' +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", department='" + department + '\'' +
               ", position='" + position + '\'' +
               ", salary=" + salary +
               ", hireDate=" + hireDate +
               ", employmentType=" + employmentType +
               ", skills=" + skills +
               '}';
    }
}

// Made with Bob
