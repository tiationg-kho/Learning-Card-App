package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    @NotNull
    private String commenterId;
    @NotNull
    private Integer cardId;
}
