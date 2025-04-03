package com.example.demo.services;

import com.example.demo.dtos.CreateVehicleDTO;
import com.example.demo.dtos.UpdateVehicleDTO;
import com.example.demo.dtos.RestoreVehicleDTO;
import com.example.demo.entities.VehicleEntity;
import com.example.demo.filters.VehicleFilter;
import com.example.demo.repositories.abstraction.VehicleRepositoryInterface;
import com.example.demo.services.implementation.VehicleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepositoryInterface vehicleRepository;

    private VehicleEntity mockVehicle;

    @BeforeEach
    void setUp() {
        RestoreVehicleDTO restoreDto = new RestoreVehicleDTO("Model X", "Tesla", 2022, "Electric Car", false, null, null);
        mockVehicle = VehicleEntity.restore(1L, restoreDto);
    }

    @Test
    void createVehicle_ShouldReturnId() {
        CreateVehicleDTO dto = new CreateVehicleDTO("Model X", "Tesla", 2022, "Electric Car", false);
        when(vehicleRepository.create(any(VehicleEntity.class))).thenReturn(1L);

        Long vehicleId = vehicleService.createVehicle(dto);

        assertEquals(1L, vehicleId);
        verify(vehicleRepository, times(1)).create(any(VehicleEntity.class));
    }

    @Test
    void getVehicleById_ShouldReturnVehicle() {
        when(vehicleRepository.findById(1L)).thenReturn(mockVehicle);

        VehicleEntity result = vehicleService.getVehicleById(1L);

        assertEquals(mockVehicle, result);
        verify(vehicleRepository, times(1)).findById(1L);
    }

    @Test
    void getVehicleByFilter_ShouldReturnList() {
        VehicleFilter filter = new VehicleFilter("Model X", "Tesla", 2022, "Electric Car", false, null);
        when(vehicleRepository.findByFilter(filter)).thenReturn(List.of(mockVehicle));

        List<VehicleEntity> result = vehicleService.getVehicleByFilter(filter);

        assertEquals(1, result.size());
        assertEquals(mockVehicle, result.get(0));
        verify(vehicleRepository, times(1)).findByFilter(filter);
    }

    @Test
    void updateVehicle_ShouldReturnTrue() {
        UpdateVehicleDTO dto = new UpdateVehicleDTO("Model S", null, null, null, null);
        when(vehicleRepository.findById(1L)).thenReturn(mockVehicle);
        when(vehicleRepository.update(eq(1L), any(VehicleEntity.class))).thenReturn(true);

        boolean result = vehicleService.updateVehicle(1L, dto);

        assertTrue(result);
        verify(vehicleRepository, times(1)).findById(1L);
        verify(vehicleRepository, times(1)).update(eq(1L), any(VehicleEntity.class));
    }

    @Test
    void markAsSold_ShouldReturnTrue() {
        when(vehicleRepository.findById(1L)).thenReturn(mockVehicle);
        when(vehicleRepository.update(eq(1L), any(VehicleEntity.class))).thenReturn(true);

        boolean result = vehicleService.markAsSold(1L);

        assertTrue(result);
        verify(vehicleRepository, times(1)).findById(1L);
        verify(vehicleRepository, times(1)).update(eq(1L), any(VehicleEntity.class));
    }
}