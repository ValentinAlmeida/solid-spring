package com.example.demo.controllers;

import com.example.demo.entities.VehicleEntity;
import com.example.demo.exceptions.VehicleNotFoundException;
import com.example.demo.requests.CreateVehicleRequest;
import com.example.demo.requests.UpdateVehicleRequest;
import com.example.demo.requests.VehicleFilterRequest;
import com.example.demo.supports.VehicleEntitySerializer;
import com.example.demo.services.abstraction.VehicleServiceInterface;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleServiceInterface vehicleService;

    public VehicleController(VehicleServiceInterface vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createVehicle(@Valid @RequestBody CreateVehicleRequest request) {
        Long vehicleId = vehicleService.createVehicle(request.toDTO());
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

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getVehicleByFilter(@Valid VehicleFilterRequest request) {
        List<VehicleEntity> vehicles = vehicleService.getVehicleByFilter(request.toFilter());
        
        List<Map<String, Object>> serializedVehicles = vehicles.stream()
            .map(VehicleEntitySerializer::serialize)
            .toList();
    
            return ResponseEntity.ok(Map.of(
                "vehicles", serializedVehicles,
                "count", serializedVehicles.size()
            ));
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllVehicles() {
        List<VehicleEntity> vehicles = vehicleService.getAllVehicles();
        
        List<Map<String, Object>> serializedVehicles = vehicles.stream()
            .map(VehicleEntitySerializer::serialize)
            .toList();
        
        return ResponseEntity.ok(Map.of(
            "vehicles", serializedVehicles,
            "count", serializedVehicles.size()
        ));
    }

    @GetMapping("/{id}/detailed")
    public ResponseEntity<Map<String, Object>> getVehicleDetailed(@PathVariable Long id) {
        VehicleEntity vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(VehicleEntitySerializer.serializeWithDetails(vehicle));
    }

    @PatchMapping("/{id}")
    public void updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVehicleRequest request) {
        vehicleService.updateVehicle(id, request.toDTO());
    }

    @PostMapping("/{id}/sold")
    public void markAsSold(@PathVariable Long id) {
        vehicleService.markAsSold(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateVehicleFully(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVehicleRequest request) {
        vehicleService.updateVehicle(id, request.toDTO());
        VehicleEntity updatedVehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(VehicleEntitySerializer.serializeWithDetails(updatedVehicle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        boolean deleted = vehicleService.deleteVehicle(id);
        if (!deleted) {
            throw new VehicleNotFoundException("Vehicle not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}