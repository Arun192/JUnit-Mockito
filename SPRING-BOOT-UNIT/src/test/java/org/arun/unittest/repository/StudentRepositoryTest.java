package org.arun.unittest.repository;

import org.arun.unittest.abstactclass.AbstractContainerBaseTest;
import org.arun.unittest.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//Disable in memory database support
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private StudentRepository studentRepository;

    //JUnit for  save student operation -- BDD style
    @DisplayName("JUnit for  save student operation")
    @Test
    public void givenStudentObject_whenSave_thenReturnSavedStudent(){

        // given - setup or precondition
        Student student = Student.builder()
                .firstName("Pankaj")
                .lastName("Yadav")
                .email("pankajy@gmail.com")
                .build();
        //when - action or the testing
       Student savedStudent;
        savedStudent = studentRepository.save(student);

        //then - verify output
        Assertions.assertNotNull(savedStudent);
        Assertions.assertNotNull(savedStudent.getId());


    }
}