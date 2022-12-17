package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CardRequest {
    @NotBlank
    private String upTitle;
    @NotBlank
    private String upContent;
    @NotBlank
    private String downTitle;
    @NotBlank
    private String downContent;

    @NotNull
    private Integer cardsetId;
}
