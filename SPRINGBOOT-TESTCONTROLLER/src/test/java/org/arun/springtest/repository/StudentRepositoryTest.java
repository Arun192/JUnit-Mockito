package org.arun.springtest.repository;

import org.arun.springtest.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setup() {
        student = Student.builder()

                .firstName("Arun")
                .lastName("Prajapati")
                .email("arunp@gmail.com")
                .build();

    }

    @DisplayName("givenStudentObject_whenSave_thenReturnSavedStudent")
    @Test
    public void givenStudentObject_whenSave_thenReturnSavedStudent() {

        //given - precondition or setup

        //when - action or the behaviour that we are going to test
        Student savedStudent = studentRepository.save(student);


        //then - verify the output
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId());

    }

    //Junit test for
    @Test
    public void givenStudentList_whenFindAll_thenStudentList() {
        // given precondition or setup
        Student student1 = Student.builder()

                .firstName("Raj")
                .lastName("Prajapati")
                .email("rahulkp@gmail.com")
                .build();
        Student student2 = Student.builder()

                .firstName("Ram")
                .lastName("Yadav")
                .email("ramg@gmail.com")
                .build();

        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);


        // when -action or the behaviour that we are going to test
        List<Student> students = studentRepository.findAll();

        //then - verify the output
        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(3);
    }

    //Junit test for
    @Test
    public void givenStudentObject_whenFindById_thenReturnStudentObject() {
        // given precondition or setup
        Student student3 = Student.builder()
                .firstName("Kavi")
                .lastName("Tulsi")
                .email("kavit@gmail.com")
                .build();
        studentRepository.save(student3);
        // when -action or the behaviour that we are going to test
        Student studentDB = studentRepository.findById(student3.getId()).get();

        //then - verify the output
        assertThat(studentDB).isNotNull();
    }

    @Test
    public void givenStudentObject_whenDelete_thenReturnStudentObject() {
        // given precondition or setup
        Student student3 = Student.builder()
                .id(111)
                .firstName("Kavi")
                .lastName("Tulsi")
                .email("kavit@gmail.com")
                .build();
        studentRepository.save(student3);
        studentRepository.deleteById(student3.getId());
        // when -action or the behaviour that we are going to test
        Optional<Student> deleteStudent = studentRepository.findById(student3.getId());

        //then - verify the output
        assertThat(deleteStudent).isEmpty();
    }
}
