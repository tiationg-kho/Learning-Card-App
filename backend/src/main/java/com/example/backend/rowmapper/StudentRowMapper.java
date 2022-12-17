package com.example.backend.rowmapper;

import com.example.backend.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("studentId"));
        student.setEmail(rs.getString("email"));
        student.setPwd(rs.getString("pwd"));
        student.setCreatedDate(rs.getTimestamp("createdDate"));
        student.setModifiedDate(rs.getTimestamp("modifiedDate"));
        return student;
    }
}
