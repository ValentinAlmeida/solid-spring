package com.example.demo.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.dtos.CreateVehicleDTO;
import com.example.demo.entities.VehicleEntity;
import com.example.demo.exceptions.VehicleNotFoundException;
import com.example.demo.mappers.VehicleMapper;
import com.example.demo.model.VehicleModel;
import com.example.demo.repositories.abstraction.VehicleRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VehicleRepositoryTest {

    @Mock
    private VehicleRepositoryInterface vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    private VehicleEntity vehicleEntity;
    private VehicleModel vehicleModel;

    @BeforeEach
    void setUp() {
        CreateVehicleDTO createDTO = new CreateVehicleDTO("Sedan", "Toyota", 2022, "Descrição", false);
        vehicleEntity = VehicleEntity.create(createDTO);

        vehicleModel = new VehicleModel();
        vehicleModel.setId(1L);
        vehicleModel.setModel("Sedan");
    }

    @Test
    void testCreate() {
        when(vehicleRepository.create(vehicleEntity)).thenReturn(1L);
    
        Long id = vehicleRepository.create(vehicleEntity);
    
        assertNotNull(id);
        assertEquals(1L, id);
        verify(vehicleRepository).create(vehicleEntity);
    }    

    @Test
    void testFindById_WhenVehicleExists() {
        when(vehicleRepository.findById(1L)).thenReturn(vehicleEntity.withId(1L));

        VehicleEntity result = vehicleRepository.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId().orElse(null));
    }

    @Test
    void testFindById_WhenVehicleDoesNotExist() {
        when(vehicleRepository.findById(1L)).thenThrow(new VehicleNotFoundException("Vehicle not found with id: 1"));

        assertThrows(VehicleNotFoundException.class, () -> vehicleRepository.findById(1L));
    }

    @Test
    void testUpdate_WhenVehicleExists() {
        when(vehicleRepository.update(1L, vehicleEntity)).thenReturn(true);

        boolean result = vehicleRepository.update(1L, vehicleEntity);

        assertTrue(result);
    }

    @Test
    void testUpdate_WhenVehicleDoesNotExist() {
        when(vehicleRepository.update(1L, vehicleEntity)).thenReturn(false);

        boolean result = vehicleRepository.update(1L, vehicleEntity);

        assertFalse(result);
    }
}
