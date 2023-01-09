package com.arun.springboot.controller;

import com.arun.springboot.model.Employee;
import com.arun.springboot.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {

        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployee() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") long employeeId) {
        return employeeService.getEmployeeById(employeeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity updateEmployee(@PathVariable("employeeId")
                                         long employeeId, @RequestBody Employee employee) {

        return employeeService.getEmployeeById(employeeId)
                .map(savedEmployee -> {
                    savedEmployee.setLastName(employee.getFirstName());
                    savedEmployee.setLastName(employee.getLastName());
                    savedEmployee.setEmail(employee.getEmail());

                    Employee updateEmployee = employeeService.updateEmployee(savedEmployee);

                    return new ResponseEntity(updateEmployee, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") long employeeId) {
        employeeService.deleteEmployee(employeeId);

        return new ResponseEntity<String >("Employee deleted Successfully !",HttpStatus.OK);
    }
}
