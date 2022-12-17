package com.example.backend.rowmapper;

import com.example.backend.constant.CardsetCategory;
import com.example.backend.constant.CardsetStatus;
import com.example.backend.model.Cardset;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardsetRowMapper implements RowMapper<Cardset> {
    @Override
    public Cardset mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cardset cardset = new Cardset();
        cardset.setCardsetId(rs.getInt("cardsetId"));
        cardset.setTitle(rs.getString("title"));
        cardset.setCardsetCategory(CardsetCategory.valueOf(rs.getString("cardsetCategory")));
        cardset.setCardsetStatus(CardsetStatus.valueOf(rs.getString("cardsetStatus")));
        cardset.setCreatedDate(rs.getTimestamp("createdDate"));
        cardset.setModifiedDate(rs.getTimestamp("modifiedDate"));
        cardset.setStudentId(rs.getInt("studentId"));
        return cardset;
    }
}
