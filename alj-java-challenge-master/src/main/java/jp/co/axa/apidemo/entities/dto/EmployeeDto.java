package jp.co.axa.apidemo.entities.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private Long employeeId;
    private String employeeName;
    private Integer employeeSalary;
    private String employeeDepartment;

    public EmployeeDto(Long employeeId, String employeeName, Integer employeeSalary, String employeeDepartment) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.employeeDepartment = employeeDepartment;
    }
}
