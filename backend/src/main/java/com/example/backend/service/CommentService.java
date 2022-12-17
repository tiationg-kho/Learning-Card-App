package com.example.backend.service;

import com.example.backend.dto.CommentRequest;
import com.example.backend.model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentService {

    Comment create(Integer studentId, Integer cardsetId, Integer cardId, CommentRequest commentRequest);

    List<Comment> readAll(Integer studentId, Integer cardsetId, Integer cardId);

    void delete(Integer studentId, Integer cardsetId, Integer cardId, Integer commentId);
}
