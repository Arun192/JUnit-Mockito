package com.arun.springboot.integration;

import com.arun.springboot.model.Employee;
import com.arun.springboot.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// In case of Integration Testing Mocking is not required !
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class EmployeeControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();
    }

    //Junit Integration test for Create Employee Method
    @DisplayName("Junit Integration test for Create Employee Method")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        // given precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fegade")
                .email("ramesh@gmail.com")
                .build();

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employee.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())));
    }

    @DisplayName("JUnit Integration test case for Get All Employees ")
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
        employeeRepository.saveAll(listOfEmployees);

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(listOfEmployees.size())));
    }


    //Junit test for get employee by id REST API
    @DisplayName("Positive Scenario Integration- Junit test for get employee by id REST API")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // given precondition or setup
        long employeeId = 1l;
        Employee employee = Employee.builder()
                .firstName("Arun")
                .lastName("Prajapati")
                .email("arunp@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{employeeId}", employee.getId()));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    //negative scenario test for GET employee by id
    //Junit test for get employee by id REST API
    @DisplayName("Negative Scenario   Integration - Junit test for get employee by id REST API")
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        // given precondition or setup
        long employeeId = 1l;
        Employee employee = Employee.builder()
                .firstName("Arun")
                .lastName("Prajapati")
                .email("arunp@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{employeeId}", employeeId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //Junit Integration test for update Employee REST API
    @DisplayName("Positive Scenario Integration- Junit test for update Employee REST API")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {
        // given precondition or setup

        Employee savedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Suryavanshi")
                .email("ramv@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Suryavanshi_Prajapati")
                .email("rameshvp@gmail.com")
                .build();

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{employeeId}", savedEmployee.getId())
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

        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));


        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //Junit Integration test for delete employee REST API
    @DisplayName("Integration test for delete employee REST API")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        // given precondition or setup

        Employee savedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Suryavanshi")
                .email("ramv@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);


        // when -action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/employees/{employeeId}", savedEmployee.getId()));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
