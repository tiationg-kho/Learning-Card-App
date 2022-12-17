package com.example.backend.dao.implement;

import com.example.backend.dao.CommentDao;
import com.example.backend.dto.CommentRequest;
import com.example.backend.model.Comment;
import com.example.backend.rowmapper.CommentRowMapper;
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
public class CommentDaoImplement implements CommentDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Transactional
    public Integer create(CommentRequest commentRequest) {
        String sql = "INSERT INTO comments(title, content, createdDate, modifiedDate, commenterId, cardId) VALUE (:title, :content, :createdDate, :modifiedDate, :commenterId, :cardId)";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("title", commentRequest.getTitle());
        mapSqlParameterSource.addValue("content", commentRequest.getContent());
        mapSqlParameterSource.addValue("createdDate", new Date());
        mapSqlParameterSource.addValue("modifiedDate", new Date());
        mapSqlParameterSource.addValue("commenterId", commentRequest.getCommenterId());
        mapSqlParameterSource.addValue("cardId", commentRequest.getCardId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder);
        Integer commentId = keyHolder.getKey().intValue();
        return commentId;
    }

    @Override
    public Comment readById(Integer commentId) {
        String sql = "SELECT commentId, title, content, createdDate, modifiedDate, commenterId, cardId FROM comments WHERE commentId = :commentId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("commentId", commentId);
        List<Comment> comments = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new CommentRowMapper());
        if (comments.size() == 0) {
            return null;
        }
        return comments.get(0);
    }

    @Override
    public List<Comment> readAll(Integer cardId) {
        String sql = "SELECT commentId, title, content, createdDate, modifiedDate, commenterId, cardId FROM comments WHERE cardId = :cardId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("cardId", cardId);
        List<Comment> comments = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new CommentRowMapper());
        return comments;
    }

    @Override
    @Transactional
    public void delete(Integer cardId, Integer commentId) {
        String sql = "DELETE FROM comments WHERE cardId = :cardId AND commentId = :commentId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);
        mapSqlParameterSource.addValue("cardId", cardId);
        mapSqlParameterSource.addValue("commentId", commentId);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }
}
