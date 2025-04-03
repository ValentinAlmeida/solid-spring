package com.example.demo.services.abstraction;

import com.example.demo.dtos.CreateVehicleDTO;
import com.example.demo.dtos.UpdateVehicleDTO;
import com.example.demo.entities.VehicleEntity;

public interface VehicleServiceInterface {
    
    Long createVehicle(CreateVehicleDTO dto);

    VehicleEntity getVehicleById(Long id);

    Boolean updateVehicle(Long id, UpdateVehicleDTO dto);

    Boolean markAsSold(Long id);
}
