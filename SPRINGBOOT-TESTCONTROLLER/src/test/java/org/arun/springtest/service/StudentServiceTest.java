package org.arun.springtest.service;

import org.arun.springtest.entity.Student;
import org.arun.springtest.exception.ResourceNotFoundException;
import org.arun.springtest.repository.StudentRepository;
import org.arun.springtest.service.impl.StudentServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = Student.builder()
                .firstName("Ram")
                .lastName("Raja")
                .email("ramr@gmail.com")
                .build();
    }

    @DisplayName("Junit test for savedStudent Method")
    @Test
    public void givenStudentObject_whenSaveStudent_thenReturnStudentObject() {
        // given precondition or setup

        given(studentRepository.findByEmail(student.getEmail()))
                .willReturn(Optional.empty());
        given(studentRepository.save(student)).willReturn(student);
        System.out.println(studentRepository);

        // when -action or the behaviour that we are going to test
        Student savedStudent = studentService.saveStudent(student);
        System.out.println(savedStudent);

        //then - verify the output
        assertThat(savedStudent).isNotNull();
    }

    @DisplayName("Junit test for savedStudent Method Which throws Exceptions")
    @Test
    public void givenExistingEmail_whenSavedStudent_thenThrowsException() {

        // given precondition or setup


//        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(new Student()));
//        // when -action or the behaviour that we are going to test
//        assertThrows(ResourceNotFoundException.class, () -> studentService.saveStudent(student));

        //then - verify the output

        // Haridas

        when(studentRepository.existsById(anyLong())).thenReturn(true);

        assertThrows(ResourceNotFoundException.class, ()->studentService.saveStudent(student));

        verify(studentRepository).existsById(student.getId());


    }

    //Junit test for
    @DisplayName("JUnit Test Case for Find All Students")
    @Test
    public void givenStudentObject_whenSavedStudent_thenReturnAllStudent() {
        // given precondition or setup
        given(studentRepository.findAll()).willReturn(List.of(student));

        // when -action or the behaviour that we are going to test
        List<Student> allStudents = studentService.getAllStudents();

        //then - verify the output
        assertThat(allStudents).isNotNull();
        assertThat(allStudents.size()).isEqualTo(1);
    }

    @DisplayName("JUnit Test Case for Find All Students (Negative Scenario)")
    @Test
    public void givenStudentObject_whenSavedStudent_thenReturnAllStudentEmptyList() {
        // given precondition or setup
        given(studentRepository.findAll()).willReturn(Collections.emptyList());

        // when -action or the behaviour that we are going to test
        List<Student> allStudents = studentService.getAllStudents();

        //then - verify the output
        assertThat(allStudents).isNotNull();
        assertThat(allStudents.size()).isEqualTo(0);
    }

    //Junit test for StudentId Which return Student Object
    @Test
    public void givenStudentId_whenGetStudent_thenReturnStudentObject() {
        // given precondition or setup

        student = Student.builder()
                .id(101L)
                .firstName("Ram")
                .lastName("Raja")
                .email("ramr@gmail.com")
                .build();

        given(studentRepository.findById(101L)).willReturn(Optional.of(student));

        // when -action or the behaviour that we are going to test
        Student student1 = studentService.getStudentById(student.getId()).get();

        //then - verify the output
        assertThat(student1).isEqualTo(student1);
    }

    //Junit test for
    @Test
    public void givenStudentObject_whenUpdateStudent_thenStudentObject() {
        // given precondition or setup
        given(studentRepository.save(student)).willReturn(student);
        student.setId(111L);
        student.setFirstName("Arun");
        student.setLastName("Prajapati");
        student.setEmail("arunp@gmail.com");

        // when -action or the behaviour that we are going to test
        Student updatedStudent = studentService.updateStudent(student);

        //then - verify the output
        assertThat(updatedStudent.getId()).isEqualTo(111L);
        assertThat(updatedStudent.getFirstName()).isEqualTo("Arun");
        assertThat(updatedStudent.getLastName()).isEqualTo("Prajapati");
        assertThat(updatedStudent.getEmail()).isEqualTo("arunp@gmail.com");
    }

    //Junit test for
    @Test
    public void givenStudentObject_whenDeleteStudent_thenReturnNothing() {

        // given precondition or setup
        long studentId = 101L;
        willDoNothing().given(studentRepository).deleteById(101L);
        // when -action or the behaviour that we are going to test
        studentRepository.deleteById(101L);

        //then - verify the output
        verify(studentRepository, times(1)).deleteById(studentId);
    }
}