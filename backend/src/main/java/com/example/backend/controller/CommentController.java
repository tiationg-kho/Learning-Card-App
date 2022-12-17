package com.example.backend.controller;

import com.example.backend.dto.CommentRequest;
import com.example.backend.model.Comment;
import com.example.backend.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{studentId}/{cardsetId}/{cardId}")
    public ResponseEntity<Comment> create(@PathVariable Integer studentId, @PathVariable Integer cardsetId, @PathVariable Integer cardId, @RequestBody @Valid CommentRequest commentRequest) {
        Comment comment = commentService.create(studentId, cardsetId, cardId, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @GetMapping("/{studentId}/{cardsetId}/{cardId}")
    public ResponseEntity<List<Comment>> readAll(@PathVariable Integer studentId, @PathVariable Integer cardsetId, @PathVariable Integer cardId) {
        List<Comment> comments = commentService.readAll(studentId, cardsetId, cardId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @DeleteMapping("/{studentId}/{cardsetId}/{cardId}")
    public ResponseEntity<String> delete(@PathVariable Integer studentId, @PathVariable Integer cardsetId, @PathVariable Integer cardId, @RequestParam Integer commentId) {
        commentService.delete(studentId, cardsetId, cardId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
