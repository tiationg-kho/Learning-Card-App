package com.example.backend.dao;

import com.example.backend.constant.CardsetCategory;
import com.example.backend.constant.CardsetStatus;
import com.example.backend.dto.CardsetRequest;
import com.example.backend.model.Cardset;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CardsetDao {

    Integer create(CardsetRequest cardsetRequest);

    Cardset readById(Integer cardsetId);

    List<Cardset> readAll(Integer studentId, CardsetCategory cardsetCategory, CardsetStatus cardsetStatus, String keyword, Integer limit, Integer offset);

    Integer countAll(Integer studentId, CardsetCategory cardsetCategory, CardsetStatus cardsetStatus, String keyword, Integer limit, Integer offset);

    void delete(Integer studentId, Integer cardsetId);

    void updateModifiedDateById(Integer cardsetId);
}
