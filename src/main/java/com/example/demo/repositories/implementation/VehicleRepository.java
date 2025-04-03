package com.example.demo.repositories.implementation;

import com.example.demo.entities.VehicleEntity;
import com.example.demo.exceptions.VehicleNotFoundException;
import com.example.demo.mappers.VehicleMapper;
import com.example.demo.model.VehicleModel;
import com.example.demo.repositories.abstraction.VehicleRepositoryInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleRepository implements VehicleRepositoryInterface {

    @PersistenceContext
    private EntityManager entityManager;

    private final VehicleMapper vehicleMapper;

    public VehicleRepository(VehicleMapper vehicleMapper) {
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    @Transactional
    public Long create(VehicleEntity vehicleEntity) {
        VehicleModel model = vehicleMapper.toModel(vehicleEntity);
        entityManager.persist(model);
        entityManager.flush();
        return model.getId();
    }

    @Override
    public VehicleEntity findById(Long id) {
        VehicleModel model = entityManager.find(VehicleModel.class, id);
        
        if (model == null) {
            throw new VehicleNotFoundException("Vehicle not found with id: " + id);
        }
        
        return vehicleMapper.toEntity(model);
    }

    @Override
    @Transactional
    public boolean update(Long id, VehicleEntity vehicleEntity) {
        VehicleModel existingModel = entityManager.find(VehicleModel.class, id);

        if (existingModel == null) {
            return false;
        }

        vehicleMapper.updateModel(existingModel, vehicleEntity);
        entityManager.merge(existingModel);
        return true;
    }
}
