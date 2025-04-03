package com.example.demo.supports;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class MultiplesSumEdgeCasesTest {

    @ParameterizedTest
    @CsvSource({
        "0, 0",
        "1, 0",
        "3, 0",
        "5, 3",
        "15, 45",
        "1000000, 233333166668"
    })
    void calculate_EdgeCases_ReturnsCorrectSum(int limit, long expected) {
        MultiplesSumCalculator calculator = MultiplesSumCalculator.create(limit);
        assertEquals(expected, calculator.calculate());
    }

    @Test
    void calculate_WithNegativeLimit_ReturnsZero() {
        MultiplesSumCalculator calculator = MultiplesSumCalculator.create(-10);
        assertEquals(0, calculator.calculate());
    }
}