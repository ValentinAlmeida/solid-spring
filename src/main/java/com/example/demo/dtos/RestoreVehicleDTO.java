package com.example.demo.dtos;

import java.time.LocalDateTime;

public record RestoreVehicleDTO (
    String model,
    String brand,
    Integer year,
    String description,
    Boolean sold,
    LocalDateTime created,
    LocalDateTime updated
) {}