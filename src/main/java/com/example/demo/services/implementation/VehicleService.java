package com.example.demo.services.implementation;

import com.example.demo.dtos.CreateVehicleDTO;
import com.example.demo.dtos.UpdateVehicleDTO;
import com.example.demo.entities.VehicleEntity;
import com.example.demo.repositories.abstraction.VehicleRepositoryInterface;
import com.example.demo.services.abstraction.VehicleServiceInterface;
import org.springframework.stereotype.Service;

@Service
public class VehicleService implements VehicleServiceInterface {

    private final VehicleRepositoryInterface vehicleRepository;

    public VehicleService(VehicleRepositoryInterface vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Long createVehicle(CreateVehicleDTO dto) {
        VehicleEntity newVehicle = VehicleEntity.create(dto);
        return vehicleRepository.create(newVehicle);
    }

    @Override
    public VehicleEntity getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Boolean updateVehicle(Long id, UpdateVehicleDTO dto) {
        VehicleEntity existingEntity = vehicleRepository.findById(id);

        CreateVehicleDTO createVehicleDTO = new CreateVehicleDTO(
            dto.model() != null ? dto.model() : existingEntity.getProperties().getModel(),
            dto.brand() != null ? dto.brand() : existingEntity.getProperties().getBrand(),
            dto.year() != null ? dto.year() : existingEntity.getProperties().getYear(),
            dto.description() != null ? dto.description() : existingEntity.getProperties().getDescription(),
            dto.sold() != null ? dto.sold() : existingEntity.getProperties().isSold()
        );

        return vehicleRepository.update(id, VehicleEntity.create(createVehicleDTO));
    }

    @Override
    public Boolean markAsSold(Long id) {
        VehicleEntity entity = vehicleRepository.findById(id);
        VehicleEntity soldEntity = entity.markAsSold();
        return vehicleRepository.update(id, soldEntity);
    }
}
