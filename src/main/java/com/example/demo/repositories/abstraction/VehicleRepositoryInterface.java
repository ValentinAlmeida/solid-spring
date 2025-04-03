package com.example.demo.repositories.abstraction;

import com.example.demo.entities.VehicleEntity;

public interface VehicleRepositoryInterface {

    Long create(VehicleEntity vehicleEntity);

    VehicleEntity findById(Long id);

    boolean update(Long id, VehicleEntity vehicleEntity);
}
