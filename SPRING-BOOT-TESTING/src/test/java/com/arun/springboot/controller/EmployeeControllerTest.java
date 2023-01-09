package com.arun.springboot.controller;

import com.arun.springboot.model.Employee;
import com.arun.springboot.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    //Junit test for Create Employee Method
    @DisplayName("Junit test for Create Employee Method")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fegade")
                .email("ramesh@gmail.com")
                .build();
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocation -> invocation.getArgument(0)));

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    //JUnit test case for Get All Employees
    @DisplayName("JUnit test case for Get All Employees ")
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        // given precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Ramesh")
                .lastName("Fegade").email("ramesh@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Arun")
                .lastName("Prajapati").email("arun@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Pankaj")
                .lastName("Sonkar").email("pankaj@gmail.com").build());

        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(listOfEmployees.size())));
    }

    //positive scenario - valid employee id
    //negative test for GET employee by id
    //Junit test for get employee by id REST API
    @DisplayName("Positive - Junit test for get employee by id REST API")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // given precondition or setup
        long employeeId = 1l;
        Employee employee = Employee.builder()
                .firstName("Arun")
                .lastName("Prajapati")
                .email("arunp@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{employeeId}", employeeId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    //negative scenario test for GET employee by id
    //Junit test for get employee by id REST API
    @DisplayName("Negative - Junit test for get employee by id REST API")
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        // given precondition or setup
        long employeeId = 1l;
        Employee employee = Employee.builder()
                .firstName("Arun")
                .lastName("Prajapati")
                .email("arunp@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{employeeId}", employeeId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //Need to ask different Name
    //Positive Scenario
    //Junit test for update Employee REST API
    @DisplayName("Positive Scenario - Junit test for update Employee REST API")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {
        // given precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Suryavanshi")
                .email("ramv@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Suryavanshi_Prajapati")
                .email("rameshvp@gmail.com")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));


        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));

    }

    //Negative Scenario
    //Junit test for update Employee REST API
    @DisplayName("Negative Scenario - Junit test for update Employee REST API")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
        // given precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Ram")
                .lastName("Suryavanshi")
                .email("ramv@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Ram")
                .lastName("Suryavanshi_Prajapati")
                .email("rameshvp@gmail.com")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));


        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //Junit test for delete employee REST API
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        // given precondition or setup
        long employeeId = 1l;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/employees/{employeeId}", employeeId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
