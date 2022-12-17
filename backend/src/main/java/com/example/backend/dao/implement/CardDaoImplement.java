package com.example.backend.dao.implement;

import com.example.backend.dao.CardDao;
import com.example.backend.dto.CardRequest;
import com.example.backend.model.Card;
import com.example.backend.rowmapper.CardRowMapper;
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
public class CardDaoImplement implements CardDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Transactional
    public Integer create(CardRequest cardRequest) {
        String sql = "INSERT INTO cards(upTitle, upContent, downTitle, downContent, createdDate, modifiedDate, cardsetId) VALUE (:upTitle, :upContent, :downTitle, :downContent, :createdDate, :modifiedDate, :cardsetId)";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("upTitle", cardRequest.getUpTitle());
        mapSqlParameterSource.addValue("upContent", cardRequest.getUpContent());
        mapSqlParameterSource.addValue("downTitle", cardRequest.getDownTitle());
        mapSqlParameterSource.addValue("downContent", cardRequest.getDownContent());
        mapSqlParameterSource.addValue("createdDate", new Date());
        mapSqlParameterSource.addValue("modifiedDate", new Date());
        mapSqlParameterSource.addValue("cardsetId", cardRequest.getCardsetId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder);
        Integer cardId = keyHolder.getKey().intValue();
        return cardId;
    }

    @Override
    public Card readById(Integer cardId) {
        String sql = "SELECT cardId, upTitle, upContent, downTitle, downContent, createdDate, modifiedDate, cardsetId FROM cards WHERE cardId = :cardId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("cardId", cardId);
        List<Card> cards = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new CardRowMapper());
        if (cards.size() == 0) {
            return null;
        }
        return cards.get(0);
    }

    @Override
    public List<Card> readAll(Integer cardsetId) {
        String sql = "SELECT cardId, upTitle, upContent, downTitle, downContent, createdDate, modifiedDate, cardsetId FROM cards WHERE cardsetId = :cardsetId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("cardsetId", cardsetId);
        List<Card> cards = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new CardRowMapper());
        return cards;
    }

    @Override
    @Transactional
    public void delete(Integer cardsetId, Integer cardId) {
        String sql = "DELETE FROM cards WHERE cardsetId = :cardsetId AND cardId = :cardId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("cardsetId", cardsetId);
        mapSqlParameterSource.addValue("cardId", cardId);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

    @Override
    @Transactional
    public void update(Integer cardId, CardRequest cardRequest) {
        String sql = "UPDATE cards SET upTitle = :upTitle, upContent = :upContent, downTitle = :downTitle, downContent = :downContent, modifiedDate = :modifiedDate WHERE cardId = :cardId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("upTitle", cardRequest.getUpTitle());
        mapSqlParameterSource.addValue("upContent", cardRequest.getUpContent());
        mapSqlParameterSource.addValue("downTitle", cardRequest.getDownTitle());
        mapSqlParameterSource.addValue("downContent", cardRequest.getDownContent());
        mapSqlParameterSource.addValue("modifiedDate", new Date());
        mapSqlParameterSource.addValue("cardId", cardId);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }
}
