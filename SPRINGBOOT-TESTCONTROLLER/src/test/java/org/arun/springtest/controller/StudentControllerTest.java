package org.arun.springtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.arun.springtest.entity.Student;
import org.arun.springtest.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentControllerTest.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("JUnit Test for Save Employee")
    @Test
    public void givenStudentObject_whenCreateStudent_thenReturnSavedStudent() throws Exception {
        // given precondition or setup
        Student student = Student.builder()
                .id(101L)
                .firstName("Pankaj")
                .lastName("Sonkar")
                .email("sak@gmail.com")
                .build();


        mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
