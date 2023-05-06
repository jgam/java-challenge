package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotExistException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Cacheable(value = "employees")
    public List<Employee> retrieveEmployees() {
        List<Employee> employees;
        try{
            employees = employeeRepository.findAll();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return employees;
    }

    @Cacheable(value = "employee", key="#employeeId")
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> employee;
        try{
            employee = employeeRepository.findById(employeeId);
        }catch(EntityNotFoundException entityNotFoundException){
            throw entityNotFoundException;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return employee.get();
    }

    @Cacheable(value = "employee", key="#employeeId")
    public void saveEmployee(Employee employee){
        try{
            if(employee != null){
                employeeRepository.save(employee);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }

    }

    @CacheEvict(value = "employee", key="#employeeId")
    public void deleteEmployee(Long employeeId){
        if(employeeExists(employeeId)){
            employeeRepository.deleteById(employeeId);
        }else{
            throw new EmployeeNotExistException("Cant delete since employee does not exist");
        }
    }

    @CachePut(value = "employee", key="#employeeId")
    public Employee updateEmployee(Long employeeId, Employee employee) {
        if(employeeExists(employeeId)){
            employee.setId(employeeId);
            return employeeRepository.save(employee);
        }else{
            throw new EmployeeNotExistException("Cant update since employee does not exist");
        }
    }

    private boolean employeeExists(Long employeeId){
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isPresent()){
            return true;
        }
        return false;
    }
}