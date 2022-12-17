package com.example.backend.service.implement;

import com.example.backend.dao.StudentDao;
import com.example.backend.dto.StudentRequest;
import com.example.backend.model.Student;
import com.example.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class StudentServiceImplement implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public Student signup(StudentRequest studentRequest) {
        studentRequest.setPwd(DigestUtils.md5DigestAsHex(studentRequest.getPwd().getBytes()));
        Student student = studentDao.readByEmail(studentRequest.getEmail());
        if (student != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Integer studentId = studentDao.create(studentRequest);
        student = studentDao.readById(studentId);
        return student;
    }

    @Override
    public Student signin(StudentRequest studentRequest) {
        studentRequest.setPwd(DigestUtils.md5DigestAsHex(studentRequest.getPwd().getBytes()));
        Student student = studentDao.readByEmail(studentRequest.getEmail());
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!student.getPwd().equals(studentRequest.getPwd())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return student;
    }
}
