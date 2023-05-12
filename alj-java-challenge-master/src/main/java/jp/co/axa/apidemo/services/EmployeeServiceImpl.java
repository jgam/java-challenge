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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

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

    @Cacheable(cacheNames = "employees", key = "#employeeId")
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> employee;
        try{
            employee = employeeRepository.findById(employeeId);
            if(employee == null){
                throw new EmployeeNotExistException("Employee not exist with the given employee ID: " + employeeId);
            }
        }catch(EntityNotFoundException entityNotFoundException){
            throw entityNotFoundException;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return employee.get();
    }

    @Transactional
    @CacheEvict(value = "employees", allEntries = true)
    public Employee saveEmployee(Employee employee){
        try{
            if(employee != null){
                employee = employeeRepository.save(employee);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return employee;
    }

    @CacheEvict(value = "employees", allEntries = true)
    public Boolean deleteEmployee(Long employeeId){
        if(employeeExists(employeeId)){
            employeeRepository.deleteById(employeeId);
        }else{
            throw new EmployeeNotExistException("Cant delete since employee does not exist");
        }
        return true;
    }

    @CachePut(value = "employee", key="#employeeId")
    @CacheEvict(value = "employees", allEntries = true)
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