package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.*;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.entities.dto.EmployeeDto;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;


    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

//    public void setEmployeeService(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }

    @GetMapping("/employees")
    @ApiOperation(value = "The API gets all the employees in the system", response= EmployeeDto.class)
    @ApiResponses(value = {
            @ApiResponse(code= 200, message="Successfully Retrieved"),
            @ApiResponse(code = 401, message="Unauthorized"),
            @ApiResponse(code = 403, message="Forbidden"),
            @ApiResponse(code = 404, message="Resource Not Found"),
            @ApiResponse(code = 500, message="Internal Server Error", response = EmployeeDto.class, responseContainer = "List")
    })
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        logging("Retrieving Employees..");
        List<Employee> employees = employeeService.retrieveEmployees();
        logging("Retrieved employees successfully!");
        return new ResponseEntity<>(
                employees
                        .stream()
                        .map(e -> new EmployeeDto(e.getId(), e.getName(), e.getSalary(), e.getDepartment()))
                        .collect(Collectors.toList())
                , HttpStatus.OK);
    }

    @GetMapping("/employees/{employeeId}")
    @ApiOperation(value = "The API gets single employee in the system", response= EmployeeDto.class)
    @ApiResponses(value = {
            @ApiResponse(code= 200, message="Successfully Retrieved"),
            @ApiResponse(code = 401, message="Unauthorized"),
            @ApiResponse(code = 403, message="Forbidden"),
            @ApiResponse(code = 404, message="Resource Not Found"),
            @ApiResponse(code = 500, message="Internal Server Error", response = EmployeeDto.class)
    })
    public ResponseEntity<EmployeeDto> getEmployee(@ApiParam(value="employeeId", required=true) @PathVariable(name="employeeId")Long employeeId) {
        logging("Retrieving a single employee : " + employeeId);
        Employee retrievedEmployee = employeeService.getEmployee(employeeId);
        logging("Retrieved a single employee : " + employeeId);

        return new ResponseEntity<>(new EmployeeDto(retrievedEmployee.getId(), retrievedEmployee.getName(), retrievedEmployee.getSalary(), retrievedEmployee.getDepartment()), HttpStatus.OK);
    }

    @PostMapping("/employees")
    @ApiOperation(value="The API created employee record when given valid payload", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message="Successfully Created"),
            @ApiResponse(code = 401, message="Unauthorized"),
            @ApiResponse(code = 403, message="Forbidden"),
            @ApiResponse(code = 404, message="Resource Not Found"),
            @ApiResponse(code = 500, message="Internal Server Error", response = Void.class),
    })
    public ResponseEntity saveEmployee(@Valid @RequestBody Employee employee, HttpServletResponse response){
        logging("Saving Employee Name: "+employee.getName());
        employeeService.saveEmployee(employee);
        logging("Saved Employee Name : "+employee.getName());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/employees/{employeeId}")
    @ApiOperation(value = "The API deletes employee record when given valid employeeId", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message="Successfully Deleted"),
            @ApiResponse(code = 401, message="Unauthorized"),
            @ApiResponse(code = 403, message="Forbidden"),
            @ApiResponse(code = 404, message="Resource Not Found"),
            @ApiResponse(code = 500, message="Internal Server Error", response = Void.class),
    })
    public ResponseEntity deleteEmployee(@ApiParam(value = "employeeId", required=true, type = "Long") @PathVariable(name="employeeId")Long employeeId){
        logging("Deleting Employee ID: "+employeeId);
        employeeService.deleteEmployee(employeeId);
        logging("Deleted Employee ID: "+employeeId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/employees/{employeeId}")
    @ApiOperation(value = "The API updates employee record when given valid employeeId", response= EmployeeDto.class)
    @ApiResponses(value = {
            @ApiResponse(code= 200, message="Successfully Updated"),
            @ApiResponse(code = 401, message="Unauthorized"),
            @ApiResponse(code = 403, message="Forbidden"),
            @ApiResponse(code = 404, message="Resource Not Found"),
            @ApiResponse(code = 500, message="Internal Server Error", response = EmployeeDto.class)
    })
    public ResponseEntity<EmployeeDto> updateEmployee(@ApiParam(value = "employeeId", required=true, type="Long") @RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId){
        Employee emp = employeeService.getEmployee(employeeId);
        if(emp != null){
            Employee saved = employeeService.updateEmployee(employeeId, employee);
            return new ResponseEntity<>(
                    new EmployeeDto(
                            saved.getId(),
                            saved.getName(),
                            saved.getSalary(),
                            saved.getDepartment()
                    ), HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );

    }

    private void logging(String message){
        if(logger.isDebugEnabled()){
            logger.debug(message);
        }
    }

}
