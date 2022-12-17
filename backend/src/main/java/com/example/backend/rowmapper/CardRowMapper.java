package com.example.backend.rowmapper;

import com.example.backend.model.Card;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardRowMapper implements RowMapper<Card> {
    @Override
    public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
        Card card = new Card();
        card.setCardId(rs.getInt("cardId"));
        card.setUpTitle(rs.getString("upTitle"));
        card.setUpContent(rs.getString("upContent"));
        card.setDownTitle(rs.getString("downTitle"));
        card.setDownContent(rs.getString("downContent"));
        card.setCreatedDate(rs.getTimestamp("createdDate"));
        card.setModifiedDate(rs.getTimestamp("modifiedDate"));
        card.setCardsetId(rs.getInt("cardsetId"));
        return card;
    }
}
