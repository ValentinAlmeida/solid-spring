package com.example.demo.entities;

import com.example.demo.dtos.CreateVehicleDTO;
import com.example.demo.dtos.RestoreVehicleDTO;
import com.example.demo.properties.VehicleProperties;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class VehicleEntity {
    private final Long id;
    private final VehicleProperties properties;

    private VehicleEntity(VehicleProperties properties, Long id) {
        this.properties = Objects.requireNonNull(properties, "Properties cannot be null");
        this.id = id;
    }

    public static VehicleEntity create(CreateVehicleDTO dto) {
        Objects.requireNonNull(dto, "DTO cannot be null");
        
        VehicleProperties props = new VehicleProperties(
            dto.model(),
            dto.brand(),
            dto.year(),
            dto.description(),
            dto.sold(),
            null,
            null
        );
        return new VehicleEntity(props, null);
    }

    public static VehicleEntity restore(Long id, RestoreVehicleDTO dto) {
        Objects.requireNonNull(dto, "Restore DTO cannot be null");
        
        VehicleProperties props = new VehicleProperties(
            dto.model(),
            dto.brand(),
            dto.year(),
            dto.description(),
            dto.sold(),
            dto.created(),
            dto.updated()
        );
        return new VehicleEntity(props, id);
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    public VehicleProperties getProperties() { 
        return properties; 
    }

    public VehicleEntity markAsSold() {
        VehicleProperties newProps = new VehicleProperties(
            properties.getModel(),
            properties.getBrand(),
            properties.getYear(),
            properties.getDescription(),
            true,
            properties.getCreated(),
            LocalDateTime.now()
        );
        return new VehicleEntity(newProps, this.id);
    }

    public VehicleEntity updateDescription(String newDescription) {
        VehicleProperties newProps = new VehicleProperties(
            properties.getModel(),
            properties.getBrand(),
            properties.getYear(),
            newDescription,
            properties.isSold(),
            properties.getCreated(),
            LocalDateTime.now()
        );
        return new VehicleEntity(newProps, this.id);
    }

    public VehicleEntity withId(Long newId) {
        return new VehicleEntity(this.properties, newId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleEntity vehicle = (VehicleEntity) o;

        if (this.id == null && vehicle.id == null) return false;
        return Objects.equals(id, vehicle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}