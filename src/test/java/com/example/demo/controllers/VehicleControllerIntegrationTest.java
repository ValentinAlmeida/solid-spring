package com.example.demo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.requests.CreateVehicleRequest;
import com.example.demo.requests.UpdateVehicleRequest;
import com.example.demo.requests.VehicleFilterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateVehicle() throws Exception {
        CreateVehicleRequest request = new CreateVehicleRequest("Sedan", "Toyota", 2022, "Descrição", false);

        mockMvc.perform(post("/api/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void testGetVehicle() throws Exception {
        mockMvc.perform(get("/api/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateVehicle() throws Exception {
        UpdateVehicleRequest request = new UpdateVehicleRequest("Model X", "Tesla", 2023, "Elétrico", false);

        mockMvc.perform(patch("/api/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testMarkAsSold() throws Exception {
        mockMvc.perform(post("/api/vehicles/1/sold"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetVehicleByFilter() throws Exception {
        VehicleFilterRequest request = new VehicleFilterRequest("Toyota", "Sedan", 2022, null, null, null);

        mockMvc.perform(get("/api/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicles").isArray());
    }
}