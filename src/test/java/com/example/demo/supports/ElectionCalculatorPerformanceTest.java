package com.example.demo.supports;

import com.example.demo.dtos.CreateElectionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

class ElectionCalculatorPerformanceTest {

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void createAndCalculate_WithMaxIntegerValues_CompletesInTime() {
        CreateElectionDTO dto = new CreateElectionDTO(
            Integer.MAX_VALUE,
            Integer.MAX_VALUE/2,
            Integer.MAX_VALUE/4,
            Integer.MAX_VALUE/4
        );
        
        ElectionCalculator calculator = ElectionCalculator.create(dto);
        assertEquals(50.0, calculator.calculateValidVotesPercentage(), 0.001);
    }

    @Test
    @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
    void createAndCalculate_WithLargeNumbers_CompletesQuickly() {
        CreateElectionDTO dto = new CreateElectionDTO(
            10_000_000,
            5_000_000,
            3_000_000,
            2_000_000
        );
        
        ElectionCalculator calculator = ElectionCalculator.create(dto);
        assertEquals(50.0, calculator.calculateValidVotesPercentage(), 0.001);
    }
}