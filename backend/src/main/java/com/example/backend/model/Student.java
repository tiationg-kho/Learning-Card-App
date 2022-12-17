package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class Student {
    private Integer studentId;
    private String email;
    @JsonIgnore
    private String pwd;
    private Date createdDate;
    private Date modifiedDate;
}
