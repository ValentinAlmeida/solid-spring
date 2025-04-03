package com.example.demo.controllers;

import com.example.demo.dtos.CreateVehicleDTO;
import com.example.demo.dtos.RestoreVehicleDTO;
import com.example.demo.entities.VehicleEntity;
import com.example.demo.services.implementation.VehicleService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @Test
    public void createVehicle_ShouldReturnCreatedStatus() throws Exception {
        String requestBody = """
            {
                "model": "Model S",
                "brand": "Tesla",
                "year": 2023,
                "description": "Electric car",
                "sold": false
            }
            """;
        
        RestoreVehicleDTO restoreDto = new RestoreVehicleDTO(
            "Model S", "Tesla", 2023, "Electric car", false, null, null
        );
        
        VehicleEntity mockVehicle = VehicleEntity.restore(1L, restoreDto);
        
        when(vehicleService.createVehicle(any(CreateVehicleDTO.class))).thenReturn(1L);
        when(vehicleService.getVehicleById(1L)).thenReturn(mockVehicle);

        mockMvc.perform(post("/api/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.model", is("Model S")));
    }

    @Test
    public void getVehicle_ShouldReturnVehicle() throws Exception {
        RestoreVehicleDTO restoreDto = new RestoreVehicleDTO(
            "Model S", "Tesla", 2023, "Electric car", false, null, null
        );
        VehicleEntity mockVehicle = VehicleEntity.restore(1L, restoreDto);
        
        when(vehicleService.getVehicleById(1L)).thenReturn(mockVehicle);

        mockMvc.perform(get("/api/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.model", is("Model S")));
    }

    @Test
    public void getVehicleDetailed_ShouldReturnDetailedVehicle() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        RestoreVehicleDTO restoreDto = new RestoreVehicleDTO(
            "Model S", "Tesla", 2023, "Electric car", false, now, now
        );
        VehicleEntity mockVehicle = VehicleEntity.restore(1L, restoreDto);
        
        when(vehicleService.getVehicleById(1L)).thenReturn(mockVehicle);

        mockMvc.perform(get("/api/vehicles/1/detailed")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.created", notNullValue()))
                .andExpect(jsonPath("$.updated", notNullValue()));
    }

    @Test
    public void getVehicleByFilter_ShouldReturnFilteredVehicles() throws Exception {
        String requestBody = """
            {
                "model": "Model",
                "brand": "Tesla",
                "year": 2023
            }
            """;

        RestoreVehicleDTO restoreDto1 = new RestoreVehicleDTO(
            "Model S", "Tesla", 2023, "Electric car", false, null, null
        );
        RestoreVehicleDTO restoreDto2 = new RestoreVehicleDTO(
            "Model 3", "Tesla", 2023, "Compact", false, null, null
        );
        
        VehicleEntity mockVehicle1 = VehicleEntity.restore(1L, restoreDto1);
        VehicleEntity mockVehicle2 = VehicleEntity.restore(2L, restoreDto2);
        
        when(vehicleService.getVehicleByFilter(any())).thenReturn(List.of(mockVehicle1, mockVehicle2));

        mockMvc.perform(get("/api/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicles", hasSize(2)))
                .andExpect(jsonPath("$.vehicles[0].id", is(1)))
                .andExpect(jsonPath("$.vehicles[1].id", is(2)));
    }

    @Test
    public void updateVehicle_ShouldReturnNoContent() throws Exception {
        String requestBody = """
            {
                "model": "Model S Plaid",
                "description": "Updated description"
            }
            """;

        mockMvc.perform(patch("/api/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNoContent());
    }

    @Test
    public void markAsSold_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(post("/api/vehicles/1/sold")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}