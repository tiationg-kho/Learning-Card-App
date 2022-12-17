package com.example.backend.service;

import com.example.backend.dto.CardRequest;
import com.example.backend.model.Card;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Component
public interface CardService {

    Card create(Integer studentId, Integer cardsetId, CardRequest cardRequest);

    List<Card> readAll(Integer studentId, Integer cardsetId);

    void delete(Integer studentId, Integer cardsetId, Integer cardId);

    Card update(Integer studentId, Integer cardsetId, Integer cardId, CardRequest cardRequest);

    ByteArrayOutputStream export(Integer studentId, Integer cardsetId) throws IOException, SQLException;
}
