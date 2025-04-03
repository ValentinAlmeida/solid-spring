package com.example.demo.dtos;

public record UpdateVehicleDTO(
    String model,
    String brand,
    Integer year,
    String description,
    Boolean sold
) {}