package com.arun.springboot.service;

import com.arun.springboot.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(long Id);

    Employee updateEmployee(Employee  updatedEmployee);

    public  void deleteEmployee(long Id);
}
