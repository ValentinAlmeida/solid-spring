package com.example.demo.supports;

import com.example.demo.dtos.CreateElectionDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ElectionCalculatorTest {
    @Test
    void create_WithValidDTO_ReturnsCalculatorInstance() {
        CreateElectionDTO dto = new CreateElectionDTO(1000, 800, 150, 50);
        ElectionCalculator calculator = ElectionCalculator.create(dto);
        assertNotNull(calculator);
        assertEquals(80.0, calculator.calculateValidVotesPercentage(), 0.001);
    }

    @Test
    void create_WithNullDTO_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ElectionCalculator.create(null));
    }
}