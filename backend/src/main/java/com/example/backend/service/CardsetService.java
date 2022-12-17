package com.example.backend.service;

import com.example.backend.constant.CardsetCategory;
import com.example.backend.constant.CardsetStatus;
import com.example.backend.dto.CardsetRequest;
import com.example.backend.model.Cardset;
import com.example.backend.util.Page;
import org.springframework.stereotype.Component;

@Component
public interface CardsetService {

    Cardset create(Integer studentId, CardsetRequest cardsetRequest);

    Page<Cardset> readAll(Integer studentId, CardsetCategory cardsetCategory, CardsetStatus cardsetStatus, String keyword, Integer limit, Integer offset);

    void delete(Integer studentId, Integer cardsetId);
}
