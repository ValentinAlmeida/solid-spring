package com.example.demo.supports;

import com.example.demo.dtos.CreateElectionDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ElectionCalculatorEdgeCasesTest {
    @Test
    void calculate_WithZeroValidVotes_ReturnsZero() {
        CreateElectionDTO dto = new CreateElectionDTO(1000, 0, 150, 50);
        ElectionCalculator calculator = ElectionCalculator.create(dto);
        assertEquals(0.0, calculator.calculateValidVotesPercentage(), 0.001);
    }

    @Test
    void calculate_WithAllValidVotes_Returns100Percent() {
        CreateElectionDTO dto = new CreateElectionDTO(1000, 1000, 0, 0);
        ElectionCalculator calculator = ElectionCalculator.create(dto);
        assertEquals(100.0, calculator.calculateValidVotesPercentage(), 0.001);
    }

    @Test
    void calculate_WithEqualDistribution_ReturnsCorrectPercentages() {
        CreateElectionDTO dto = new CreateElectionDTO(900, 300, 300, 300);
        ElectionCalculator calculator = ElectionCalculator.create(dto);
        assertEquals(33.333, calculator.calculateValidVotesPercentage(), 0.001);
        assertEquals(33.333, calculator.calculateBlankVotesPercentage(), 0.001);
        assertEquals(33.333, calculator.calculateNullVotesPercentage(), 0.001);
    }
}