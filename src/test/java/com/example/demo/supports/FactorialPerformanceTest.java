package com.example.demo.supports;

import com.example.demo.dtos.CreateFactorialDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

class FactorialPerformanceTest {

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void calculate_SmallNumber_CompletesInTime() {
        FactorialCalculator calculator = FactorialCalculator.create(new CreateFactorialDTO(10));
        assertEquals(3_628_800L, calculator.calculate());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void calculate_MediumNumber_CompletesInTime() {
        FactorialCalculator calculator = FactorialCalculator.create(new CreateFactorialDTO(20));
        assertEquals(2_432_902_008_176_640_000L, calculator.calculate());
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void calculate_Zero_CompletesInstantly() {
        FactorialCalculator calculator = FactorialCalculator.create(new CreateFactorialDTO(0));
        assertEquals(1L, calculator.calculate());
    }
}