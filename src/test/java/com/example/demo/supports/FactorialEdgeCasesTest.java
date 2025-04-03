package com.example.demo.supports;

import com.example.demo.dtos.CreateFactorialDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class FactorialEdgeCasesTest {

    @ParameterizedTest
    @CsvSource({
        "0, 1",
        "1, 1",
        "5, 120",
        "12, 479001600"
    })
    void calculate_WithValidEdgeCases_ReturnsCorrectResult(int number, long expected) {
        FactorialCalculator calculator = FactorialCalculator.create(new CreateFactorialDTO(number));
        assertEquals(expected, calculator.calculate());
    }

    @Test
    void getNumber_ReturnsCorrectValue() {
        FactorialCalculator calculator = FactorialCalculator.create(new CreateFactorialDTO(7));
        assertEquals(7, calculator.getNumber());
    }
}