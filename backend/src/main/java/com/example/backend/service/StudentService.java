package com.example.backend.service;

import com.example.backend.dto.StudentRequest;
import com.example.backend.model.Student;
import org.springframework.stereotype.Component;

@Component
public interface StudentService {
    Student signup(StudentRequest studentRequest);

    Student signin(StudentRequest studentRequest);
}
