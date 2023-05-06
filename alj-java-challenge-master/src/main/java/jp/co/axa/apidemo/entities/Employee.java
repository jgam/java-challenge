package jp.co.axa.apidemo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="EMPLOYEE")
public class Employee {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Getter
    @NotNull(message = "name field is mandatory")
    @Size(min = 5, message="name should contain at least 5 characters")
    @Column(name="EMPLOYEE_NAME")
    private String name;

    @Getter
    @Column(name="EMPLOYEE_SALARY")
    private Integer salary;

    @Getter
    @Size(min = 2, message = "department should have at least 2 characters")
    @NotNull(message = "department is mandatory")
    @Column(name="DEPARTMENT")
    private String department;
}
