package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

public interface EmployeeService {

    public List<Employee> retrieveEmployees();

    public Employee getEmployee(Long employeeId);

    public Employee saveEmployee(Employee employee);

    public Boolean deleteEmployee(Long employeeId);

    public Employee updateEmployee(Long employeeId, Employee employee);
}