package org.arun.unittest;

import org.arun.unittest.abstactclass.AbstractContainerBaseTest;
import org.arun.unittest.entity.Student;
import org.arun.unittest.repository.StudentRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SprigBootUnitApplicationTests extends AbstractContainerBaseTest  {




    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    //given /when/then format --BDD style
    // Behaviour Driven Development Style
    @DisplayName("JUnit Test for get All Students")
    @Test
    public void givenStudent_whenGetAllStudents_thenListOfStudent() throws Exception {

        //given - setup or precondition
        List<Student> students = List.of(Student.builder()
                        .firstName("Arun")
                        .lastName("Prajapati")
                        .email("arunp@gmail.com")
                        .build(),
                Student.builder()
                        .firstName("Hari")
                        .lastName("Gupta")
                        .email("hari@gmail.com")
                        .build()
        );
        studentRepository.saveAll(students);

        //when - action
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/students"));

        //then - verify the output
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));


    }

}
