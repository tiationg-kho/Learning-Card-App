package com.example.backend.dao;

import com.example.backend.dto.StudentRequest;
import com.example.backend.model.Student;
import org.springframework.stereotype.Component;

@Component
public interface StudentDao {
    Integer create(StudentRequest studentRequest);

    Student readById(Integer studentId);

    Student readByEmail(String email);
}
