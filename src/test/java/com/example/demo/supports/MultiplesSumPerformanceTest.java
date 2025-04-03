package com.example.demo.supports;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

class MultiplesSumPerformanceTest {

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void calculate_SmallLimit_PerformsFast() {
        MultiplesSumCalculator calculator = MultiplesSumCalculator.create(1000);
        assertEquals(233168, calculator.calculate());
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void calculate_LargeLimit_PerformsWithinLimit() {
        MultiplesSumCalculator calculator = MultiplesSumCalculator.create(1000000);
        assertEquals(233333166668L, calculator.calculate());
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.MILLISECONDS)
    void calculate_MinimumLimit_PerformsInstantly() {
        MultiplesSumCalculator calculator = MultiplesSumCalculator.create(1);
        assertEquals(0, calculator.calculate());
    }
}