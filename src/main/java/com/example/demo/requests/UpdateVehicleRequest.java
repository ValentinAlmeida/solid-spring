package com.example.demo.requests;

import com.example.demo.dtos.UpdateVehicleDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateVehicleRequest(
    @Size(max = 100, message = "Model name must be at most 100 characters") String model,
    @Size(max = 100, message = "Brand name must be at most 100 characters") String brand,
    @Min(value = 1886, message = "Year must be 1886 or later") @Positive Integer year,
    @Size(max = 500, message = "Description must be at most 500 characters") String description,
    Boolean sold
) {
    public UpdateVehicleDTO toDTO() {
        return new UpdateVehicleDTO(
            model,
            brand,
            year,
            description,
            sold
        );
    }
}
