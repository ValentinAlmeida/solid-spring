package com.example.demo.supports;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.demo.dtos.CreateFactorialDTO;

class FactorialCalculatorTest {

    @ParameterizedTest
    @CsvSource({
        "0, 1",
        "1, 1",
        "2, 2",
        "3, 6",
        "4, 24",
        "5, 120",
        "6, 720"
    })
    void calculate_WithValidNumbers_ReturnsCorrectResult(int number, long expected) {
        FactorialCalculator calculator = FactorialCalculator.create(new CreateFactorialDTO(number));
        assertEquals(expected, calculator.calculate());
    }

    @Test
    void create_WithNullDTO_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            FactorialCalculator.create(null);
        });
    }
}