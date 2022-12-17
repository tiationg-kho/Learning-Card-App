package com.example.backend.controller;

import com.example.backend.dto.StudentRequest;
import com.example.backend.model.Student;
import com.example.backend.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/signup")
    public ResponseEntity<Student> signup(@RequestBody @Valid StudentRequest studentRequest) {
        Student student = studentService.signup(studentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @PostMapping("/signin")
    public ResponseEntity<Student> signin(@RequestBody @Valid StudentRequest studentRequest) {
        Student student = studentService.signin(studentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }
}
