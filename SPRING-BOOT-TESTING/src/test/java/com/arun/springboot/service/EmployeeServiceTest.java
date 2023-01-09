package com.arun.springboot.service;

import com.arun.springboot.exception.ResourceNotFoundException;
import com.arun.springboot.model.Employee;
import com.arun.springboot.repository.EmployeeRepository;
import com.arun.springboot.service.imp.EmployeeServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImp employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        //        employeeRepository = Mockito.mock(EmployeeRepository.class);
        //        employeeService = new EmployeeServiceImp(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("Arun")
                .lastName("Prajapati")
                .email("arunp@gmail.com")
                .build();
    }

    //Junit test for savedEmployee Method
    @DisplayName("Junit test for savedEmployee Method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
        System.out.println(employeeRepository);

        // when -action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);
        System.out.println(savedEmployee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for savedEmployee Method Which throws Exceptions
    @DisplayName("Junit test for savedEmployee Method Which throws Exceptions")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        // given precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));
//        given(employeeRepository.save(employee)).willReturn(employee);
//        System.out.println(employeeRepository);

        // when -action or the behaviour that we are going to test
        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.saveEmployee(employee));

        //then
        verify(employeeRepository, never()).save(any(Employee.class));
    }


    //Junit test for getAllEmployees method
    @DisplayName("Junit test for getAllEmployees method")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        // given precondition or setup

        employee = Employee.builder()
                .id(2L)
                .firstName("Ram")
                .lastName("Prajapati")
                .email("ramp@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee));

        // when -action or the behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(1);
    }

    //Junit test for getAllEmployees method (negative scenario)
    @DisplayName("Junit test for getAllEmployees method(negative scenario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        // given precondition or setup

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when -action or the behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);

    }

    //Junit test for EmployeeById method
    @DisplayName("Junit test for EmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when -action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();
        System.out.println(savedEmployee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //NEED TO ASKED
    //Junit test for updateEmployee method
    @DisplayName("Junit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setId(11L);
        employee.setLastName("ArunP");
        employee.setLastName("Prajapati2");
        employee.setEmail("arunp2@gmail.com");


        // when -action or the behaviour that we are going to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then - verify the output
        assertThat(updatedEmployee.getId()).isEqualTo(11L);
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Arun");
        assertThat(updatedEmployee.getLastName()).isEqualTo("Prajapati2");
        assertThat(updatedEmployee.getEmail()).isEqualTo("arunp2@gmail.com");
    }

    // Very Important Test Cases
    //Junit test for delete Employee method
    @DisplayName("Junit test for delete Employee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        // given precondition or setup
        // Call wiilDoNothing API for Void Method
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when -action or the behaviour that we are going to test
        employeeService.deleteEmployee(employeeId);

        //then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}
