package com.example.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6)
    private String pwd;
}
