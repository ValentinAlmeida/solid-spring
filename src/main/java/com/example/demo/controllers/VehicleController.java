package com.example.demo.controllers;

import com.example.demo.dtos.CreateVehicleDTO;
import com.example.demo.dtos.UpdateVehicleDTO;
import com.example.demo.entities.VehicleEntity;
import com.example.demo.supports.VehicleEntitySerializer;
import com.example.demo.services.abstraction.VehicleServiceInterface;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleServiceInterface vehicleService;

    public VehicleController(VehicleServiceInterface vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createVehicle(@Valid @RequestBody CreateVehicleDTO dto) {
        Long vehicleId = vehicleService.createVehicle(dto);
        VehicleEntity createdVehicle = vehicleService.getVehicleById(vehicleId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(VehicleEntitySerializer.serializeWithDetails(createdVehicle));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getVehicle(@PathVariable Long id) {
        VehicleEntity vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(VehicleEntitySerializer.serialize(vehicle));
    }

    @GetMapping("/{id}/detailed")
    public ResponseEntity<Map<String, Object>> getVehicleDetailed(@PathVariable Long id) {
        VehicleEntity vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(VehicleEntitySerializer.serializeWithDetails(vehicle));
    }

    @PatchMapping("/{id}")
    public void updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVehicleDTO dto) {
        vehicleService.updateVehicle(id, dto);
    }

    @PostMapping("/{id}/sold")
    public void markAsSold(@PathVariable Long id) {
        vehicleService.markAsSold(id);
    }
}