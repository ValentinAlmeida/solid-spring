package com.example.demo.repositories.abstraction;

import java.util.List;

import com.example.demo.entities.VehicleEntity;
import com.example.demo.filters.VehicleFilter;

public interface VehicleRepositoryInterface {

    Long create(VehicleEntity vehicleEntity);

    VehicleEntity findById(Long id);

    List<VehicleEntity> findByFilter(VehicleFilter filter);

    boolean update(Long id, VehicleEntity vehicleEntity);
}
