package com.example.backend.rowmapper;

import com.example.backend.model.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRowMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();
        comment.setCommentId(rs.getInt("commentId"));
        comment.setTitle(rs.getString("title"));
        comment.setContent(rs.getString("content"));
        comment.setCreatedDate(rs.getTimestamp("createdDate"));
        comment.setModifiedDate(rs.getTimestamp("modifiedDate"));
        comment.setCommenterId(rs.getString("commenterId"));
        comment.setCardId(rs.getInt("cardId"));
        return comment;
    }
}
