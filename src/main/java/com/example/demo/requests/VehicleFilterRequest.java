package com.example.demo.requests;

import com.example.demo.filters.VehicleFilter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.RequestParam;

public record VehicleFilterRequest(
    @RequestParam(required = false) @Size(max = 100, message = "Model name must be at most 100 characters") String model,
    @RequestParam(required = false) @Size(max = 100, message = "Brand name must be at most 100 characters") String brand,
    @RequestParam(required = false) @Min(value = 1886, message = "Year must be 1886 or later") @Positive Integer year,
    @RequestParam(required = false) @Size(max = 500, message = "Description must be at most 500 characters") String description,
    @RequestParam(required = false) Boolean sold,
    @RequestParam(required = false) LocalDateTime created
) {
    public VehicleFilter toFilter() {
        return new VehicleFilter(
            model,
            brand,
            year,
            description,
            sold,
            created
        );
    }
}
