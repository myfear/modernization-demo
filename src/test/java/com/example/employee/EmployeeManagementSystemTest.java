package com.example.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

import com.example.employee.EmployeeManagementSystem.SimpleEmployee;
import com.example.employee.EmployeeManagementSystem.EmployeeUtils;

@DisplayName("Employee Management System Tests")
class EmployeeManagementSystemTest {
    
    private EmployeeManagementSystem system;
    
    @BeforeEach
    void setUp() {
        system = new EmployeeManagementSystem();
    }
    
    @Test
    @DisplayName("Should initialize with 5 employees")
    void shouldInitializeWithEmployees() {
        List<com.example.employee.model.Employee> employees = system.getAllEmployees();
        assertThat(employees).hasSize(5);
    }
    
    @Test
    @DisplayName("Should return correct department budget")
    void shouldReturnDepartmentBudget() {
        assertThat(system.getDepartmentBudget("Engineering")).isEqualTo("$500,000");
        assertThat(system.getDepartmentBudget("Sales")).isEqualTo("$300,000");
        assertThat(system.getDepartmentBudget("HR")).isEqualTo("$150,000");
        assertThat(system.getDepartmentBudget("Unknown")).isEqualTo("Unknown");
    }
    
    @Test
    @DisplayName("Should process String value correctly")
    void shouldProcessStringValue() {
        String result = system.processValue("hello");
        assertThat(result).isEqualTo("String: HELLO");
    }
    
    @Test
    @DisplayName("Should process Integer value correctly")
    void shouldProcessIntegerValue() {
        String result = system.processValue(42);
        assertThat(result).isEqualTo("Number: 84");
    }
    
    @Test
    @DisplayName("Should process Employee value correctly")
    void shouldProcessEmployeeValue() {
        SimpleEmployee emp = new SimpleEmployee("E999", "Test User", "IT", 50000);
        String result = system.processValue(emp);
        assertThat(result).isEqualTo("Employee: Test User");
    }
    
    @Test
    @DisplayName("Should handle unknown type")
    void shouldHandleUnknownType() {
        String result = system.processValue(3.14);
        assertThat(result).isEqualTo("Unknown type");
    }
    
    @Test
    @DisplayName("Should find employee department by valid ID")
    void shouldFindEmployeeDepartment() {
        String department = system.getEmployeeDepartment("E001");
        assertThat(department).isEqualTo("Engineering");
    }
    
    @Test
    @DisplayName("Should return not found for invalid employee ID")
    void shouldReturnNotFoundForInvalidId() {
        String result = system.getEmployeeDepartment("E999");
        assertThat(result).isEqualTo("Employee not found");
    }
    
    @Test
    @DisplayName("Should generate report with correct structure")
    void shouldGenerateReport() {
        String report = system.generateReport();
        assertThat(report)
            .contains("=== Employee Report ===")
            .contains("Total Employees: 5")
            .contains("Departments:")
            .contains("Average Salary:")
            .contains("======================");
    }
    
    @Test
    @DisplayName("Should generate email template with correct format")
    void shouldGenerateEmailTemplate() {
        String email = system.generateEmailTemplate("Alice Johnson", 5000);
        assertThat(email)
            .contains("Dear Alice Johnson")
            .contains("$5000.00")
            .contains("HR Department");
    }
    
    @Test
    @DisplayName("Should return unmodifiable list of employee names")
    void shouldReturnUnmodifiableEmployeeNames() {
        List<String> names = system.getEmployeeNames();
        assertThat(names).hasSize(5);
        assertThatThrownBy(() -> names.add("New Name"))
            .isInstanceOf(UnsupportedOperationException.class);
    }
    
    @Test
    @DisplayName("Should filter high earners up to limit")
    void shouldFilterHighEarners() {
        List<com.example.employee.model.Employee> highEarners = system.getHighEarnersUpToLimit(80000, 2);
        assertThat(highEarners)
            .hasSize(2)
            .allMatch(e -> e.getSalary() >= 80000);
        
        // Should be sorted by salary descending
        assertThat(highEarners.get(0).getSalary())
            .isGreaterThanOrEqualTo(highEarners.get(1).getSalary());
    }
    
    @Test
    @DisplayName("Should return empty list when no employees meet criteria")
    void shouldReturnEmptyListWhenNoCriteriaMet() {
        List<com.example.employee.model.Employee> highEarners = system.getHighEarnersUpToLimit(200000, 5);
        assertThat(highEarners).isEmpty();
    }
    
    @Test
    @DisplayName("Should return random employee from list")
    void shouldReturnRandomEmployee() {
        com.example.employee.model.Employee random = system.getRandomEmployee();
        assertThat(random).isNotNull();
        assertThat(system.getAllEmployees()).contains(random);
    }
    
    @Test
    @DisplayName("Employee should have correct properties")
    void employeeShouldHaveCorrectProperties() {
        SimpleEmployee emp = new SimpleEmployee("E100", "John Doe", "IT", 60000);
        assertThat(emp.getId()).isEqualTo("E100");
        assertThat(emp.getName()).isEqualTo("John Doe");
        assertThat(emp.getDepartment()).isEqualTo("IT");
        assertThat(emp.getSalary()).isEqualTo(60000);
    }
    
    @Test
    @DisplayName("Employees with same data should be equal")
    void employeesShouldBeEqual() {
        SimpleEmployee emp1 = new SimpleEmployee("E100", "John Doe", "IT", 60000);
        SimpleEmployee emp2 = new SimpleEmployee("E100", "John Doe", "IT", 60000);
        assertThat(emp1).isEqualTo(emp2);
        assertThat(emp1.hashCode()).isEqualTo(emp2.hashCode());
    }
    
    @Test
    @DisplayName("Employee toString should contain all fields")
    void employeeToStringShouldContainFields() {
        SimpleEmployee emp = new SimpleEmployee("E100", "John Doe", "IT", 60000);
        String str = emp.toString();
        assertThat(str)
            .contains("E100")
            .contains("John Doe")
            .contains("IT")
            .contains("60000");
    }
    
    @Test
    @DisplayName("EmployeeUtils should format salary correctly")
    void shouldFormatSalary() {
        assertThat(EmployeeUtils.formatSalary(5000.0)).isEqualTo("$5000.00");
        assertThat(EmployeeUtils.formatSalary(5000.5)).isEqualTo("$5000.50");
        assertThat(EmployeeUtils.formatSalary(5000.99)).isEqualTo("$5000.99");
    }
}

// Made with Bob
