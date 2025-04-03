package com.example.demo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MultiplesSumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void calculateSum_WithLimit10_Returns23() throws Exception {
        mockMvc.perform(get("/api/multiples/sum")
                .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                    "A soma dos múltiplos de 3 ou 5 abaixo de 10 é 23"
                ));
    }

    @Test
    void calculateSum_WithLimit20_Returns78() throws Exception {
        mockMvc.perform(get("/api/multiples/sum")
                .param("limit", "20"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                    "A soma dos múltiplos de 3 ou 5 abaixo de 20 é 78"
                ));
    }
}