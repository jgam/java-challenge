package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotExistException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeServiceTest {
    @InjectMocks
    EmployeeServiceImpl employeeService;
    @Mock
    EmployeeRepository employeeRepository;

    List<Employee> employeeList = new ArrayList<>();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        Employee developer = new Employee(1L, "SDE1", 100, "TECH");
        Employee pm = new Employee(2L, "PM1", 120, "MANAGEMENT");

        employeeList.add(developer);
        employeeList.add(pm);
    }

    @Test
    public void getEmployeeTest_success_whenProperEmployeeId(){
        //given
        Long empId = 1L;

        //when
        when(employeeRepository.findById(empId)).thenReturn(Optional.ofNullable(employeeList.get(0)));
        Employee employee = employeeService.getEmployee(empId);

        //then
        assertThat(employee).isEqualTo(employeeList.get(0));
    }

    @Test(expected = EmployeeNotExistException.class)
    public void getEmployeeTest_EmployeeNotExistThrown(){
        //given
        Long empId = 3L;

        //when
        when(employeeRepository.findById(empId)).thenReturn(null);
        employeeService.getEmployee(empId);

        //then
        Assert.fail("Exception should occur");
    }

    @Test(expected = RuntimeException.class)
    public void getEmployeeTest_runtimeExceptionThrown(){
        //given
        Long empId = 1L;

        //when
        when(employeeRepository.findById(empId)).thenThrow(new RuntimeException("Something in the server error occurred"));
        employeeService.getEmployee(empId);

        //then
        Assert.fail("Exception should occur");
    }

    @Test
    public void getAllEmployeesTest_success(){
        //given - use before in init

        //when
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> employees = employeeService.retrieveEmployees();

        //then
        assertThat(employees.size()).isEqualTo(employeeList.size());
        assertThat(employees.get(0).getId()).isEqualTo(employeeList.get(0).getId());
    }

    @Test(expected = Exception.class)
    public void getAllEmployeesTest_ExceptionThrown(){
        //given

        //when
        when(employeeRepository.findAll()).thenThrow(new Exception());

        //then
        employeeService.retrieveEmployees();

        Assert.fail("Exception should occur");
    }

    @Test
    public void saveEmployeeTest_success_whenProperPayloadGiven(){
        //given
        Employee inputEmployee = new Employee(1L, "Test", 100, "Testing");

        //when
        when(employeeRepository.save(inputEmployee)).thenReturn(inputEmployee);
        Employee retrieved = employeeService.saveEmployee(inputEmployee);

        //then
        assertThat(inputEmployee.getId()).isEqualTo(retrieved.getId());
        assertThat(inputEmployee.getName()).isEqualTo(retrieved.getName());
        assertThat(inputEmployee.getDepartment()).isEqualTo(retrieved.getDepartment());
        assertThat(inputEmployee.getSalary()).isEqualTo(retrieved.getSalary());
    }

    @Test(expected = Exception.class)
    public void saveEmployeeTest_ExceptionThrown(){
        // given
        Employee inputEmployee = new Employee(1L, "Test", 100, "Testing");

        //when
        when(employeeRepository.save(inputEmployee)).thenThrow(new Exception());
        employeeService.saveEmployee(inputEmployee);

        //then
        Assert.fail("Exception should occur");
    }

    @Test
    public void updateEmployeeTest_success_whenProperPayloadGiven(){
        Long employeeId = 2L;
        Employee inputEmployee = new Employee(2L, "Marketing", 200, "MQ");

        // given
        when(employeeRepository.save(inputEmployee)).thenReturn(inputEmployee);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(inputEmployee));
        Employee updated = employeeService.updateEmployee(employeeId, inputEmployee);

        // then
        assertThat(updated).isEqualTo(inputEmployee);
        assertThat(updated.getName()).isEqualTo(inputEmployee.getName());
        assertThat(updated.getSalary()).isEqualTo(inputEmployee.getSalary());
        assertThat(updated.getDepartment()).isEqualTo(inputEmployee.getDepartment());
    }

    @Test(expected = EmployeeNotExistException.class)
    public void updateEmployeeTest_fails_whenEmployeeIDNotFound(){
        Long employeeId = 3L;
        Employee inputEmployee = new Employee(2L, "Marketing", 200, "MQ");

        // given
        when(employeeRepository.save(inputEmployee)).thenReturn(inputEmployee);
        when(employeeRepository.findById(employeeId)).thenThrow(new EmployeeNotExistException());
        Employee updated = employeeService.updateEmployee(employeeId, inputEmployee);

        // then
        Assert.fail("Exception should occur");
    }

    @Test
    public void deleteEmployeeTest_success(){
        Long empId = 2L;
        when(employeeRepository.findById(empId)).thenReturn(Optional.ofNullable(employeeList.get(1)));
        Boolean result = employeeService.deleteEmployee(empId);

        assertThat(result).isEqualTo(true);
    }

    @Test(expected = EmployeeNotExistException.class)
    public void deleteEmployeeTest_ExceptionThrown_whenNotExistingEmployeeIdGiven(){
        Long empId = 2L;
        when(employeeRepository.findById(empId)).thenThrow(new EmployeeNotExistException());
        Boolean result = employeeService.deleteEmployee(empId);

        Assert.fail("Exception should occur");
    }
}
