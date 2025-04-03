package com.example.demo.properties;

import java.time.LocalDateTime;
import java.util.Objects;

public final class VehicleProperties {
    private final String model;
    private final String brand;
    private final Integer year;
    private final String description;
    private final Boolean sold;
    private final LocalDateTime created;
    private final LocalDateTime updated;

    public VehicleProperties(
        String model, 
        String brand, 
        Integer year,
        String description, 
        Boolean sold,
        LocalDateTime created, 
        LocalDateTime updated
    ) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.brand = Objects.requireNonNull(brand, "Brand cannot be null");
        this.year = Objects.requireNonNull(year, "Year cannot be null");
        this.description = description;
        this.sold = Objects.requireNonNull(sold, "Sold status cannot be null");
        this.created = created;
        this.updated = updated;
    }

    public String getModel() { return model; }
    public String getBrand() { return brand; }
    public Integer getYear() { return year; }
    public String getDescription() { return description; }
    public Boolean isSold() { return sold; }
    public LocalDateTime getCreated() { return created; }
    public LocalDateTime getUpdated() { return updated; }
}