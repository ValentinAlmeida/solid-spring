package com.example.demo.requests;

import com.example.demo.dtos.CreateFactorialDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FactorialRequest(
    @Min(0) @NotNull int number
) {
    public CreateFactorialDTO toDTO() {
        return new CreateFactorialDTO(number);
    }
}