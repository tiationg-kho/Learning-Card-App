package com.example.backend.service.implement;

import com.example.backend.constant.CardsetCategory;
import com.example.backend.constant.CardsetStatus;
import com.example.backend.dao.CardsetDao;
import com.example.backend.dao.StudentDao;
import com.example.backend.dto.CardsetRequest;
import com.example.backend.model.Cardset;
import com.example.backend.model.Student;
import com.example.backend.service.CardsetService;
import com.example.backend.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
public class CardsetServiceImplement implements CardsetService {

    @Autowired
    private CardsetDao cardsetDao;
    @Autowired
    private StudentDao studentDao;

    @Override
    public Cardset create(Integer studentId, CardsetRequest cardsetRequest) {
        if (studentId != cardsetRequest.getStudentId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Student student = studentDao.readById(studentId);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Integer cardsetId = cardsetDao.create(cardsetRequest);
        Cardset cardset = cardsetDao.readById(cardsetId);
        return cardset;
    }

    @Override
    public Page<Cardset> readAll(Integer studentId, CardsetCategory cardsetCategory, CardsetStatus cardsetStatus, String keyword, Integer limit, Integer offset) {
        Student student = studentDao.readById(studentId);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Page<Cardset> page = new Page<>();
        page.setObjects(cardsetDao.readAll(studentId, cardsetCategory, cardsetStatus, keyword, limit, offset));
        page.setOffset(offset);
        page.setLimit(limit);
        page.setTotal(cardsetDao.countAll(studentId, cardsetCategory, cardsetStatus, keyword, limit, offset));
        return page;
    }

    @Override
    public void delete(Integer studentId, Integer cardsetId) {
        Student student = studentDao.readById(studentId);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        cardsetDao.delete(studentId, cardsetId);
    }

}
