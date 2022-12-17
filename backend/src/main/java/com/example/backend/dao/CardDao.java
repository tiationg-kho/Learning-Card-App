package com.example.backend.dao;

import com.example.backend.dto.CardRequest;
import com.example.backend.model.Card;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CardDao {

    Integer create(CardRequest cardRequest);

    Card readById(Integer cardId);

    List<Card> readAll(Integer cardsetId);

    void delete(Integer cardsetId, Integer cardId);

    void update(Integer cardId, CardRequest cardRequest);

}
