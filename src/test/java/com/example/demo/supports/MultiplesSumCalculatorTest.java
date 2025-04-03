package com.example.demo.supports;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MultiplesSumCalculatorTest {

    @ParameterizedTest
    @CsvSource({
        "10, 23",
        "20, 78",
        "100, 2318",
        "1000, 233168"
    })
    void calculate_WithDifferentLimits_ReturnsCorrectSum(int limit, int expected) {
        MultiplesSumCalculator calculator = MultiplesSumCalculator.create(limit);
        assertEquals(expected, calculator.calculate());
    }

    @Test
    void create_WithNegativeLimit_ReturnsCalculator() {
        MultiplesSumCalculator calculator = MultiplesSumCalculator.create(-5);
        assertEquals(-5, calculator.getLimit());
    }
}