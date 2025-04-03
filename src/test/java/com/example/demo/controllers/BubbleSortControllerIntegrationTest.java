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
class BubbleSortControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sortArray_WithValidInput_ReturnsSortedResult() throws Exception {
        String requestBody = "{\"unsortedArray\": [5, 3, 2, 4, 7, 1, 0, 6]}";

        mockMvc.perform(get("/api/sort/bubble")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Original array: [5, 3, 2, 4, 7, 1, 0, 6]")))
                .andExpect(content().string(containsString("Sorted array: [0, 1, 2, 3, 4, 5, 6, 7]")));
    }

    @Test
    void sortArray_WithEmptyArray_ReturnsEmptyArrays() throws Exception {
        String requestBody = "{\"unsortedArray\": []}";

        mockMvc.perform(get("/api/sort/bubble")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Original array: []")))
                .andExpect(content().string(containsString("Sorted array: []")));
    }

    @Test
    void sortArray_WithNullArray_ReturnsBadRequest() throws Exception {
        String requestBody = "{\"unsortedArray\": null}";

        mockMvc.perform(get("/api/sort/bubble")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sortArray_WithMissingField_ReturnsBadRequest() throws Exception {
        String requestBody = "{\"otherField\": [1, 2, 3]}";

        mockMvc.perform(get("/api/sort/bubble")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sortArray_WithMalformedJson_ReturnsBadRequest() throws Exception {
        String requestBody = "{invalid json}";

        mockMvc.perform(get("/api/sort/bubble")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sortArray_WithInvalidArrayElements_ReturnsBadRequest() throws Exception {
        String requestBody = "{\"unsortedArray\": [1, \"two\", 3]}";

        mockMvc.perform(get("/api/sort/bubble")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sortArray_WithObjectInsteadOfArray_ReturnsBadRequest() throws Exception {
        String requestBody = "{\"unsortedArray\": {\"key\":\"value\"}}";

        mockMvc.perform(get("/api/sort/bubble")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}