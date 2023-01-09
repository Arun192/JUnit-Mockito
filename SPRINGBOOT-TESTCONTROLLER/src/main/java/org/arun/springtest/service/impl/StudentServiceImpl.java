package org.arun.springtest.service.impl;

import org.arun.springtest.entity.Student;
import org.arun.springtest.exception.ResourceNotFoundException;
import org.arun.springtest.repository.StudentRepository;
import org.arun.springtest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public Student saveStudent(Student student) {

       // Optional<Student> savedEmployee = studentRepository.findByEmail(student.getEmail());
        boolean b =studentRepository.existsById(student.getId());
        if (b) {
            throw new ResourceNotFoundException("Employee already Exist with given Email "
                    + student.getEmail());
        }

        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(long Id) {
        return studentRepository.findById(Id);
    }

    @Override
    public Student updateStudent(Student updatedEmployee) {
        return studentRepository.save(updatedEmployee);
    }

    @Override
    public void deleteStudent(long Id) {
        studentRepository.deleteById(Id);
    }
}
