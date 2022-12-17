package com.example.backend.dao.implement;

import com.example.backend.constant.CardsetCategory;
import com.example.backend.constant.CardsetStatus;
import com.example.backend.dao.CardsetDao;
import com.example.backend.dto.CardsetRequest;
import com.example.backend.model.Cardset;
import com.example.backend.rowmapper.CardsetRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CardsetDaoImplement implements CardsetDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Transactional
    public Integer create(CardsetRequest cardsetRequest) {
        String sql = "INSERT INTO cardsets(title, cardsetCategory, cardsetStatus, createdDate, modifiedDate, studentId) VALUE (:title, :cardsetCategory, :cardsetStatus, :createdDate, :modifiedDate, :studentId)";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("title", cardsetRequest.getTitle());
        mapSqlParameterSource.addValue("cardsetCategory", cardsetRequest.getCardsetCategory().name());
        mapSqlParameterSource.addValue("cardsetStatus", cardsetRequest.getCardsetStatus().name());
        mapSqlParameterSource.addValue("createdDate", new Date());
        mapSqlParameterSource.addValue("modifiedDate", new Date());
        mapSqlParameterSource.addValue("studentId", cardsetRequest.getStudentId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder);
        Integer cardsetId = keyHolder.getKey().intValue();
        return cardsetId;
    }

    @Override
    public Cardset readById(Integer cardsetId) {
        String sql = "SELECT cardsetId, title, cardsetCategory, cardsetStatus, createdDate, modifiedDate, studentId FROM cardsets WHERE cardsetId = :cardsetId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("cardsetId", cardsetId);
        List<Cardset> cardsets = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new CardsetRowMapper());
        if (cardsets.size() == 0) {
            return null;
        }
        return cardsets.get(0);
    }

    @Override
    public List<Cardset> readAll(Integer studentId, CardsetCategory cardsetCategory, CardsetStatus cardsetStatus, String keyword, Integer limit, Integer offset) {
        String sql = "SELECT cardsetId, title, cardsetCategory, cardsetStatus, createdDate, modifiedDate, studentId FROM cardsets WHERE 1 = 1";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        sql = handleConditionalSearch(studentId, cardsetCategory, cardsetStatus, keyword, sql, mapSqlParameterSource);
        sql += " ORDER BY createdDate DESC";
        sql += " LIMIT :limit";
        mapSqlParameterSource.addValue("limit", limit);
        sql += " OFFSET :offset";
        mapSqlParameterSource.addValue("offset", offset);
        List<Cardset> cardsets = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new CardsetRowMapper());
        return cardsets;
    }

    @Override
    public Integer countAll(Integer studentId, CardsetCategory cardsetCategory, CardsetStatus cardsetStatus, String keyword, Integer limit, Integer offset) {
        String sql = "SELECT COUNT(cardsetId) FROM cardsets WHERE 1 = 1";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        sql = handleConditionalSearch(studentId, cardsetCategory, cardsetStatus, keyword, sql, mapSqlParameterSource);
        Integer total = namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, Integer.class);
        return total;
    }

    private String handleConditionalSearch(Integer studentId, CardsetCategory cardsetCategory, CardsetStatus cardsetStatus, String keyword, String sql, MapSqlParameterSource mapSqlParameterSource) {
        if (cardsetStatus == null) {
            sql += " AND studentId = :studentId";
            mapSqlParameterSource.addValue("studentId", studentId);
        } else if (cardsetStatus == CardsetStatus.PUBLIC) {
            sql += " AND cardsetStatus = :cardsetStatus";
            mapSqlParameterSource.addValue("cardsetStatus", cardsetStatus.name());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (cardsetCategory != null) {
            sql += " AND cardsetCategory = :cardsetCategory";
            mapSqlParameterSource.addValue("cardsetCategory", cardsetCategory.name());
        }
        if (keyword != null) {
            sql += " AND title LIKE :keyword";
            mapSqlParameterSource.addValue("keyword", "%" + keyword + "%");
        }
        return sql;
    }

    @Override
    @Transactional
    public void delete(Integer studentId, Integer cardsetId) {
        String sql = "DELETE FROM cardsets WHERE studentId = :studentId AND cardsetId = :cardsetId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("studentId", studentId);
        mapSqlParameterSource.addValue("cardsetId", cardsetId);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

    @Override
    @Transactional
    public void updateModifiedDateById(Integer cardsetId) {
        String sql = "UPDATE cardsets SET modifiedDate = :modifiedDate WHERE cardsetId = :cardsetId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("modifiedDate", new Date());
        mapSqlParameterSource.addValue("cardsetId", cardsetId);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }
}
