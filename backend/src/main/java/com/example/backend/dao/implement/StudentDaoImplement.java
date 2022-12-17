package com.example.backend.dao.implement;

import com.example.backend.dao.StudentDao;
import com.example.backend.dto.StudentRequest;
import com.example.backend.model.Student;
import com.example.backend.rowmapper.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentDaoImplement implements StudentDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Transactional
    public Integer create(StudentRequest studentRequest) {
        String sql = "INSERT INTO students(email, pwd, createdDate, modifiedDate) VALUE (:email, :pwd, :createdDate, :modifiedDate)";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("email", studentRequest.getEmail());
        mapSqlParameterSource.addValue("pwd", studentRequest.getPwd());
        mapSqlParameterSource.addValue("createdDate", new Date());
        mapSqlParameterSource.addValue("modifiedDate", new Date());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder);
        Integer studentId = keyHolder.getKey().intValue();
        return studentId;
    }

    @Override
    public Student readById(Integer studentId) {
        String sql = "SELECT studentId, email, pwd, createdDate, modifiedDate FROM students WHERE studentId = :studentId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("studentId", studentId);
        List<Student> students = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new StudentRowMapper());
        if (students.size() == 0) {
            return null;
        }
        return students.get(0);
    }

    @Override
    public Student readByEmail(String email) {
        String sql = "SELECT studentId, email, pwd, createdDate, modifiedDate FROM students WHERE email = :email";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("email", email);
        List<Student> students = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new StudentRowMapper());
        if (students.size() == 0) {
            return null;
        }
        return students.get(0);
    }
}
