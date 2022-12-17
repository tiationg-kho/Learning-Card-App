package com.example.backend.service.implement;

import com.example.backend.constant.CardsetStatus;
import com.example.backend.dao.CardDao;
import com.example.backend.dao.CardsetDao;
import com.example.backend.dto.CardRequest;
import com.example.backend.model.Card;
import com.example.backend.model.Cardset;
import com.example.backend.service.CardService;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.List;

@Component
public class CardServiceImplement implements CardService {

    @Autowired
    private CardDao cardDao;

    @Autowired
    private CardsetDao cardsetDao;

    @Override
    @Transactional
    public Card create(Integer studentId, Integer cardsetId, CardRequest cardRequest) {
        if (cardsetId != cardRequest.getCardsetId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Cardset cardset = cardsetDao.readById(cardRequest.getCardsetId());
        if (cardset.getStudentId() != studentId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Integer cardId = cardDao.create(cardRequest);
        Card card = cardDao.readById(cardId);
        handleCardsetModifiedDate(cardsetId);
        return card;
    }

    @Override
    public List<Card> readAll(Integer studentId, Integer cardsetId) {
        Cardset cardset = cardsetDao.readById(cardsetId);
        if (cardset == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<Card> cards = cardDao.readAll(cardsetId);
        return cards;
    }

    @Override
    @Transactional
    public void delete(Integer studentId, Integer cardsetId, Integer cardId) {
        checkCardsetId(studentId, cardsetId);
        cardDao.delete(cardsetId, cardId);
        handleCardsetModifiedDate(cardsetId);
    }

    @Override
    @Transactional
    public Card update(Integer studentId, Integer cardsetId, Integer cardId, CardRequest cardRequest) {
        checkCardsetId(studentId, cardsetId);
        Card card = cardDao.readById(cardId);
        if (card == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (card.getCardsetId() != cardsetId || card.getCardsetId() != cardRequest.getCardsetId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        cardDao.update(cardId, cardRequest);
        card = cardDao.readById(cardId);
        handleCardsetModifiedDate(cardsetId);
        return card;
    }

    @Override
    public ByteArrayOutputStream export(Integer studentId, Integer cardsetId) throws IOException, SQLException {
        Cardset cardset = cardsetDao.readById(cardsetId);
        if (cardset == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (cardset.getCardsetStatus() == CardsetStatus.PRIVATE && cardset.getStudentId() != studentId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
        CSVWriter csvWriter = new CSVWriter(streamWriter);
        List<Card> cards = cardDao.readAll(cardsetId);
        String[] strings = {"idx", "upTitle", "upContent", "downTitle", "downContent", "createdDate"};
        csvWriter.writeNext(strings);
        for (int i = 0; i < cards.size(); i++) {
            Card c = cards.get(i);
            strings[0] = String.valueOf(i + 1);
            strings[1] = c.getUpTitle();
            strings[2] = c.getUpContent();
            strings[3] = c.getDownTitle();
            strings[4] = c.getDownContent();
            strings[5] = c.getCreatedDate().toString();
            csvWriter.writeNext(strings);
        }
        csvWriter.close();
        return stream;
    }

    private void checkCardsetId(Integer studentId, Integer cardsetId) {
        Cardset cardset = cardsetDao.readById(cardsetId);
        if (cardset == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (cardset.getStudentId() != studentId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private void handleCardsetModifiedDate(Integer cardsetId) {
        cardsetDao.updateModifiedDateById(cardsetId);
    }
}
