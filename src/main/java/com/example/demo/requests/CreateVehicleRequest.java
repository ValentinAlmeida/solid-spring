package com.example.demo.requests;

import com.example.demo.dtos.CreateVehicleDTO;
import jakarta.validation.constraints.*;

public record CreateVehicleRequest(
    @NotBlank String model,
    @NotBlank String brand,
    @NotNull @Positive Integer year,
    String description,
    @NotNull Boolean sold
) {
    public CreateVehicleDTO toDTO() {
        return new CreateVehicleDTO(
            model,
            brand,
            year,
            description,
            sold
        );
    }
}
