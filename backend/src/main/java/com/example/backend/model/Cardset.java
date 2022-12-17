package com.example.backend.model;

import com.example.backend.constant.CardsetCategory;
import com.example.backend.constant.CardsetStatus;
import lombok.Data;

import java.util.Date;

@Data
public class Cardset {
    private Integer cardsetId;
    private String title;
    private CardsetCategory cardsetCategory;
    private CardsetStatus cardsetStatus;
    private Date createdDate;
    private Date modifiedDate;

    private Integer studentId;
}
