package com.example.demo.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.dtos.CreateVehicleDTO;
import com.example.demo.dtos.RestoreVehicleDTO;
import com.example.demo.entities.VehicleEntity;
import com.example.demo.exceptions.VehicleNotFoundException;
import com.example.demo.filters.VehicleFilter;
import com.example.demo.mappers.VehicleMapper;
import com.example.demo.model.VehicleModel;
import com.example.demo.repositories.implementation.VehicleRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class VehicleRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private VehicleMapper vehicleMapper;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<VehicleModel> criteriaQuery;

    @Mock
    private Root<VehicleModel> root;

    @Mock
    private TypedQuery<VehicleModel> typedQuery;

    @InjectMocks
    private VehicleRepository vehicleRepository;

    private VehicleEntity vehicleEntity;
    private VehicleModel vehicleModel;

    @BeforeEach
    void setUp() {
        vehicleEntity = VehicleEntity.restore(1L, 
            new RestoreVehicleDTO(
                "Corolla",
                "Toyota",
                2020,
                "Novo",
                false,
                LocalDateTime.now(),
                null
            )
        );
        
        vehicleModel = new VehicleModel();
        vehicleModel.setId(1L);
        vehicleModel.setModel("Corolla");
        vehicleModel.setBrand("Toyota");
        vehicleModel.setYear(2020);
        vehicleModel.setDescription("Novo");
        vehicleModel.setSold(false);
        vehicleModel.setCreated(LocalDateTime.now());
    }

    @Test
    void create_ShouldPersistAndReturnId() {
        // Arrange
        CreateVehicleDTO createDto = new CreateVehicleDTO(
            "Corolla", "Toyota", 2020, "Novo", false
        );
        VehicleEntity newEntity = VehicleEntity.create(createDto);
        
        when(vehicleMapper.toModel(newEntity)).thenReturn(vehicleModel);
        doAnswer(invocation -> {
            VehicleModel model = invocation.getArgument(0);
            model.setId(1L);
            return null;
        }).when(entityManager).persist(vehicleModel);

        // Act
        Long resultId = vehicleRepository.create(newEntity);

        // Assert
        assertEquals(1L, resultId);
        verify(vehicleMapper).toModel(newEntity);
        verify(entityManager).persist(vehicleModel);
        verify(entityManager).flush();
    }

    @Test
    void findByFilter_ShouldReturnFilteredResults() {
        // Arrange
        VehicleFilter filter = new VehicleFilter("Corolla", "Toyota", 2020, null, null, null);
        List<VehicleModel> models = Arrays.asList(vehicleModel);
        
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(VehicleModel.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(VehicleModel.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(models);
        when(vehicleMapper.toEntity(vehicleModel)).thenReturn(vehicleEntity);

        // Act
        List<VehicleEntity> result = vehicleRepository.findByFilter(filter);

        // Assert
        assertEquals(1, result.size());
        assertEquals(vehicleEntity, result.get(0));
        verify(entityManager).getCriteriaBuilder();
        verify(typedQuery).getResultList();
        verify(vehicleMapper).toEntity(vehicleModel);
    }

    @Test
    void findById_ShouldReturnVehicleWhenExists() {
        // Arrange
        when(entityManager.find(VehicleModel.class, 1L)).thenReturn(vehicleModel);
        when(vehicleMapper.toEntity(vehicleModel)).thenReturn(vehicleEntity);

        // Act
        VehicleEntity result = vehicleRepository.findById(1L);

        // Assert
        assertEquals(vehicleEntity, result);
        assertEquals(Optional.of(1L), result.getId());
        assertEquals("Corolla", result.getProperties().getModel());
        assertEquals("Toyota", result.getProperties().getBrand());
        verify(entityManager).find(VehicleModel.class, 1L);
        verify(vehicleMapper).toEntity(vehicleModel);
    }

    @Test
    void findById_ShouldThrowExceptionWhenNotFound() {
        // Arrange
        when(entityManager.find(VehicleModel.class, 99L)).thenReturn(null);

        // Act & Assert
        assertThrows(VehicleNotFoundException.class, () -> vehicleRepository.findById(99L));
        verify(entityManager).find(VehicleModel.class, 99L);
    }

    @Test
    void update_ShouldReturnTrueAndUpdateWhenVehicleExists() {
        // Arrange
        when(entityManager.find(VehicleModel.class, 1L)).thenReturn(vehicleModel);
        
        // Create updated entity
        VehicleEntity updatedEntity = vehicleEntity.updateDescription("Descrição atualizada");
        
        doAnswer(invocation -> {
            VehicleModel model = invocation.getArgument(0);
            model.setDescription("Descrição atualizada");
            return null;
        }).when(vehicleMapper).updateModel(vehicleModel, updatedEntity);

        // Act
        boolean result = vehicleRepository.update(1L, updatedEntity);

        // Assert
        assertTrue(result);
        verify(entityManager).find(VehicleModel.class, 1L);
        verify(vehicleMapper).updateModel(vehicleModel, updatedEntity);
        verify(entityManager).merge(vehicleModel);
    }

    @Test
    void update_ShouldReturnFalseWhenVehicleNotFound() {
        // Arrange
        when(entityManager.find(VehicleModel.class, 99L)).thenReturn(null);

        // Act
        boolean result = vehicleRepository.update(99L, vehicleEntity);

        // Assert
        assertFalse(result);
        verify(entityManager).find(VehicleModel.class, 99L);
        verify(vehicleMapper, never()).updateModel(any(), any());
        verify(entityManager, never()).merge(any());
    }

    @Test
    void findByFilter_WithPartialFilters_ShouldReturnResults() {
        // Arrange - Filter only by brand
        VehicleFilter filter = new VehicleFilter(null, "Toyota", null, null, null, null);
        List<VehicleModel> models = Arrays.asList(vehicleModel);
        
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(VehicleModel.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(VehicleModel.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(models);
        when(vehicleMapper.toEntity(vehicleModel)).thenReturn(vehicleEntity);

        // Act
        List<VehicleEntity> result = vehicleRepository.findByFilter(filter);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Toyota", result.get(0).getProperties().getBrand());
        verify(typedQuery).getResultList();
    }
}