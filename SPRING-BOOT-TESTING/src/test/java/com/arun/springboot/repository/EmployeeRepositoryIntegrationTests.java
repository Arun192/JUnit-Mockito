package com.arun.springboot.repository;

import com.arun.springboot.integration.AbstractionBaseTest;
import com.arun.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryIntegrationTests extends AbstractionBaseTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup() {

        employee = Employee.builder()
                .firstName("Arun")
                .lastName("Prajapati")
                .email("arun@gmail.com")
                .build();

    }


    // JUnit test for save Employee operations
    @DisplayName("givenEmployeeObject_whenSave_thenReturnSavedEmployee")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Arun")
//                .lastName("Prajapati")
//                .email("arun@gmail.com")
//                .build();

        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

    //Junit test for get All Employee operation
    @DisplayName("givenEmployeeList_whenFindAll_thenEmployeeList")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList() {
        // given precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ajay")
                .lastName("Prajapti")
                .email("ajay@gmail.com")
                .build();

        Employee employee1 = Employee.builder()
                .firstName("John")
                .lastName("Cena")
                .email("cena@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        // when -action or the behaviour that we are going to test

        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2 );
    }

    //Junit test for get Employee By Id Operation
    @DisplayName("Junit test for get Employee By Id Operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployee() {
        // given precondition or setup
        Employee employee = Employee.builder()
                .firstName("Pankaj")
                .lastName("Sonkar")
                .email("pankaj@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when -action or the behaviour that we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //Junit test for get Employee by email operation
    @DisplayName("Junit test for get Employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

        // given precondition or setup
        Employee employee = Employee.builder()
                .firstName("Rohan")
                .lastName("Singh")
                .email("rohan@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when -action or the behaviour that we are going to test

        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //Junit test for Update Employee Operation
    @DisplayName("Junit test for Update Employee Operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdateEmployee() {
        // given precondition or setup
        Employee employee = Employee.builder()
                .firstName("Omkar")
                .lastName("Karade")
                .email("omkar@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when -action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setLastName("Karande");
        savedEmployee.setEmail("omkarkarande@gmail.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        //then - verify the output
        assertThat(updatedEmployee.getLastName()).isEqualTo("Karande");
        assertThat(updatedEmployee.getEmail()).isEqualTo("omkarkarande@gmail.com");
    }

    //Junit test for delete employee operation
    @DisplayName("Junit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
        // given precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Arun")
//                .lastName("Prajapati")
//                .email("arun@gmail.com")
//                .build();
        employeeRepository.save(employee);

        // when -action or the behaviour that we are going to test
        //employeeRepository.delete(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then - verify the output
        assertThat(employeeOptional).isEmpty();
    }

    //Junit test for custom query using JPQL with Index
    @DisplayName("Junit test for custom query using JPQL with Index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        // given precondition or setup
        Employee employee = Employee.builder()
                .firstName("Omkar")
                .lastName("Karande")
                .email("omkarkarande@gmail.com")
                .build();
        employeeRepository.save(employee);
        String firstName = "Omkar";
        String lastName = "Karande";

        // when -action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for custom query using JPQL with Named Params
    @DisplayName("Junit test for custom query using JPQL with Named Params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
        // given precondition or setup
        Employee employee = Employee.builder()
                .firstName("Omkar")
                .lastName("Karande")
                .email("omkarkarande@gmail.com")
                .build();
        employeeRepository.save(employee);
        String firstName = "Omkar";
        String lastName = "Karande";

        // when -action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for custom query using native SQL with index
    @DisplayName("Junit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
        // given precondition or setup
        Employee employee = Employee.builder()
                .firstName("Shiva")
                .lastName("Vaishnav")
                .email("shiva@gmail.com")
                .build();
        employeeRepository.save(employee);
        String firstName = "Shiva";
        String lastName = "Vaishnav";

        // when -action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for custom query using native SQL with index
    @DisplayName("Junit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {
        // given precondition or setup
        Employee employee = Employee.builder()
                .firstName("Shiva")
                .lastName("Vaishnav")
                .email("shiva@gmail.com")
                .build();
        employeeRepository.save(employee);
        String firstName = "Shiva";
        String lastName = "Vaishnav";

        // when -action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }


}




















