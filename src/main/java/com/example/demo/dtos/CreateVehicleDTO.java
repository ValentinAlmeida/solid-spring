package com.example.demo.dtos;

public record CreateVehicleDTO(
    String model,
    String brand,
    Integer year,
    String description,
    Boolean sold
) {}