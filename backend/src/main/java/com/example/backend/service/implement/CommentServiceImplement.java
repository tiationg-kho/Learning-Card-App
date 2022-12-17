package com.example.backend.service.implement;

import com.example.backend.constant.CardsetStatus;
import com.example.backend.dao.CardDao;
import com.example.backend.dao.CardsetDao;
import com.example.backend.dao.CommentDao;
import com.example.backend.dto.CommentRequest;
import com.example.backend.model.Card;
import com.example.backend.model.Cardset;
import com.example.backend.model.Comment;
import com.example.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class CommentServiceImplement implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CardsetDao cardsetDao;

    @Autowired
    private CardDao cardDao;

    @Override
    @Transactional
    public Comment create(Integer studentId, Integer cardsetId, Integer cardId, CommentRequest commentRequest) {
        if (cardId != commentRequest.getCardId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        check(studentId, cardsetId, cardId);
        Integer commentId = commentDao.create(commentRequest);
        Comment comment = commentDao.readById(commentId);
        return comment;
    }

    @Override
    public List<Comment> readAll(Integer studentId, Integer cardsetId, Integer cardId) {
        check(studentId, cardsetId, cardId);
        List<Comment> comments = commentDao.readAll(cardId);
        return comments;
    }

    @Override
    @Transactional
    public void delete(Integer studentId, Integer cardsetId, Integer cardId, Integer commentId) {
        check(studentId, cardsetId, cardId);
        Card card = cardDao.readById(cardId);
        Cardset cardset = cardsetDao.readById(card.getCardsetId());
        if (cardset.getStudentId() != studentId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Comment comment = commentDao.readById(commentId);
        commentDao.delete(cardId, commentId);
    }

    private void check(Integer studentId, Integer cardsetId, Integer cardId) {
        Cardset cardset = cardsetDao.readById(cardsetId);
        if (cardset == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (cardset.getCardsetStatus() == CardsetStatus.PRIVATE && cardset.getStudentId() != studentId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Card card = cardDao.readById(cardId);
        if (card == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (card.getCardsetId() != cardsetId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
