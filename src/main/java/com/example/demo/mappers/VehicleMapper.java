package com.example.demo.mappers;

import com.example.demo.dtos.RestoreVehicleDTO;
import com.example.demo.entities.VehicleEntity;
import com.example.demo.model.VehicleModel;
import com.example.demo.properties.VehicleProperties;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {
    public VehicleModel toModel(VehicleEntity entity) {
        if (entity == null) {
            return null;
        }

        VehicleModel model = new VehicleModel();
        entity.getId().ifPresent(model::setId);

        VehicleProperties props = entity.getProperties();
        model.setModel(props.getModel());
        model.setBrand(props.getBrand());
        model.setYear(props.getYear());
        model.setDescription(props.getDescription());
        model.setSold(props.isSold());

        return model;
    }

    public VehicleEntity toEntity(VehicleModel model) {
        if (model == null) {
            return null;
        }

        RestoreVehicleDTO restoreVehicleDTO = new RestoreVehicleDTO(
            model.getModel(),
            model.getBrand(),
            model.getYear(),
            model.getDescription(),
            model.getSold(),
            model.getCreated(),
            model.getUpdated()
        );

        return VehicleEntity.restore(model.getId(), restoreVehicleDTO);
    }

    public void updateModel(VehicleModel target, VehicleEntity source) {
        if (source == null || target == null) {
            return;
        }

        VehicleProperties props = source.getProperties();
        target.setModel(props.getModel());
        target.setBrand(props.getBrand());
        target.setYear(props.getYear());
        target.setDescription(props.getDescription());
        target.setSold(props.isSold());
    }
}