package com.example.backend.model;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private Integer commentId;
    private String title;
    private String content;
    private Date createdDate;
    private Date modifiedDate;

    private String commenterId;
    private Integer cardId;
}
