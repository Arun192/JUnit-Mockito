package com.arun.springboot.service.imp;

import com.arun.springboot.exception.ResourceNotFoundException;
import com.arun.springboot.model.Employee;
import com.arun.springboot.repository.EmployeeRepository;
import com.arun.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImp(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee already Exist with given Email " + employee.getEmail());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long Id) {
        return employeeRepository.findById(Id);
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployee(long Id) {
        employeeRepository.deleteById(Id);
    }
}
