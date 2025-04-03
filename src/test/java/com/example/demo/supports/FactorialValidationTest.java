package com.example.demo.supports;

import com.example.demo.dtos.CreateFactorialDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactorialValidationTest {

    @Test
    void create_WithNullDTO_ThrowsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FactorialCalculator.create(null);
        });
        assertEquals("DTO cannot be null", exception.getMessage());
    }

    @Test
    void calculate_WithNegativeNumber_ThrowsIllegalArgumentException() {
        FactorialCalculator calculator = FactorialCalculator.create(new CreateFactorialDTO(-5));
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate();
        });
        assertEquals("Number must be non-negative", exception.getMessage());
    }
}