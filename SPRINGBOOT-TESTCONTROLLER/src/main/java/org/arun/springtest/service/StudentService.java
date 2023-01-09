package org.arun.springtest.service;

import org.arun.springtest.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student saveStudent(Student employee);
    List<Student> getAllStudents();
    Optional<Student> getStudentById(long Id);

    Student updateStudent(Student  updatedEmployee);

    public  void deleteStudent(long Id);
}
