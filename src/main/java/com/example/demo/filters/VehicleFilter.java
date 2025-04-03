package com.example.demo.filters;

import java.time.LocalDateTime;

public record VehicleFilter(
    String model,
    String brand,
    Integer year,
    String description,
    Boolean sold,
    LocalDateTime created
) {}
