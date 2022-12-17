package com.example.backend.dto;

import com.example.backend.constant.CardsetCategory;
import com.example.backend.constant.CardsetStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CardsetRequest {
    @NotBlank
    private String title;
    @NotNull
    private CardsetCategory cardsetCategory;
    @NotNull
    private CardsetStatus cardsetStatus;

    @NotNull
    private Integer studentId;
}
