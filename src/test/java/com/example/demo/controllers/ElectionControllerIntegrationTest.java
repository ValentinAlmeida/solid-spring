package com.example.demo.controllers;

import com.example.demo.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc
public class ElectionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetElectionResults_Success() throws Exception {
        String requestBody = """
            {
                "totalVoters": 1000,
                "validVotes": 800,
                "blankVotes": 100,
                "nullVotes": 100
            }
            """;

        mockMvc.perform(get("/api/elections/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("valid = 80,0")));
    }

    @Test
    public void testGetElectionResults_InvalidInput() throws Exception {
        String invalidRequestBody = """
            {
                "totalVoters": -100,
                "validVotes": 80,
                "blankVotes": 10,
                "nullVotes": 10
            }
            """;

        mockMvc.perform(get("/api/elections/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetElectionResults_MissingFields() throws Exception {
        String missingFieldsBody = """
            {
                "totalVoters": 1000,
                "validVotes": 800
            }
            """;

        mockMvc.perform(get("/api/elections/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(missingFieldsBody))
                .andExpect(status().isBadRequest());
    }
}