package com.example.demo.services.abstraction;

import java.util.List;

import com.example.demo.dtos.CreateVehicleDTO;
import com.example.demo.dtos.UpdateVehicleDTO;
import com.example.demo.entities.VehicleEntity;
import com.example.demo.filters.VehicleFilter;

public interface VehicleServiceInterface {
    
    Long createVehicle(CreateVehicleDTO dto);

    VehicleEntity getVehicleById(Long id);

    List<VehicleEntity> getVehicleByFilter(VehicleFilter filter);

    Boolean updateVehicle(Long id, UpdateVehicleDTO dto);

    Boolean markAsSold(Long id);

    Boolean deleteVehicle(Long id);

    List<VehicleEntity> getAllVehicles();
}
