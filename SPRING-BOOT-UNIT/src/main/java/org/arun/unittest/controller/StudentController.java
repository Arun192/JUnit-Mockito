package org.arun.unittest.controller;

import org.arun.unittest.entity.Student;
import org.arun.unittest.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student){

        return  studentRepository.save(student);
    }

    @GetMapping
    public List<Student> getAllStudent(){
        return studentRepository.findAll();

    }
}
