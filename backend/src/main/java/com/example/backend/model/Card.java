package com.example.backend.model;

import lombok.Data;

import java.util.Date;

@Data
public class Card {
    private Integer cardId;
    private String upTitle;
    private String upContent;
    private String downTitle;
    private String downContent;
    private Date createdDate;
    private Date modifiedDate;

    private Integer cardsetId;
}
