package com.example.backend.dao;

import com.example.backend.dto.CommentRequest;
import com.example.backend.model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentDao {

    Integer create(CommentRequest commentRequest);

    Comment readById(Integer commentId);

    List<Comment> readAll(Integer cardId);

    void delete(Integer cardId, Integer commentId);
}
