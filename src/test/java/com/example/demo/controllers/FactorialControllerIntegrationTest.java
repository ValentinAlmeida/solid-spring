package com.example.demo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class FactorialControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void calculateFactorial_WithValidNumber_ReturnsResult() throws Exception {
        String requestBody = "{\"number\": 5}";

        mockMvc.perform(get("/api/math/factorial")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Factorial of 5 is 120")));
    }

    @Test
    void calculateFactorial_WithZero_ReturnsOne() throws Exception {
        String requestBody = "{\"number\": 0}";

        mockMvc.perform(get("/api/math/factorial")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Factorial of 0 is 1")));
    }

    @Test
    void calculateFactorial_WithNegativeNumber_ReturnsBadRequest() throws Exception {
        String requestBody = "{\"number\": -1}";

        mockMvc.perform(get("/api/math/factorial")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}