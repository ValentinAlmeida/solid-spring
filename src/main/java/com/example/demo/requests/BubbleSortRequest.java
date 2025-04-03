package com.example.demo.requests;

import com.example.demo.dtos.CreateBubbleSortDTO;
import jakarta.validation.constraints.NotNull;

public record BubbleSortRequest(
    @NotNull int[] unsortedArray
) {
    public CreateBubbleSortDTO toDTO() {
        return new CreateBubbleSortDTO(unsortedArray);
    }
}