package com.example.demo.supports;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import com.example.demo.dtos.CreateBubbleSortDTO;

import java.util.stream.Stream;

class BubbleSortCalculatorTest {

    private BubbleSortCalculator calculator;

    @Test
    @DisplayName("Should sort array correctly")
    void sort_WithValidArray_ReturnsSortedArray() {
        int[] input = {5, 3, 2, 4, 7, 1, 0, 6};
        int[] expected = {0, 1, 2, 3, 4, 5, 6, 7};
        
        calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(input));
        int[] result = calculator.sort();
        
        assertArrayEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideEdgeCases")
    @DisplayName("Should handle edge cases")
    void sort_WithEdgeCases_ReturnsCorrectResult(int[] input, int[] expected) {
        calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(input));
        int[] result = calculator.sort();
        assertArrayEquals(expected, result);
    }

    private static Stream<Object[]> provideEdgeCases() {
        return Stream.of(
            new Object[]{new int[]{1}, new int[]{1}},
            new Object[]{new int[]{2, 1}, new int[]{1, 2}},
            new Object[]{new int[]{1, 2, 3}, new int[]{1, 2, 3}},
            new Object[]{new int[]{3, 2, 1}, new int[]{1, 2, 3}},
            new Object[]{new int[]{-1, -5, -3}, new int[]{-5, -3, -1}},
            new Object[]{new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0}, 
                         new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}}
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should handle empty or null arrays")
    void create_WithEmptyOrNullArray_CreatesCalculator(int[] input) {
        calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(input));
        assertNotNull(calculator);
        assertArrayEquals(input == null ? new int[0] : input, calculator.getArray());
    }

    @Test
    @DisplayName("Should throw exception when DTO is null")
    void create_WithNullDTO_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            BubbleSortCalculator.create(null);
        });
    }

    @Test
    @DisplayName("GetArray should return copy of array")
    void getArray_ShouldReturnCopy() {
        int[] original = {1, 2, 3};
        calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(original));
        
        int[] firstCopy = calculator.getArray();
        int[] secondCopy = calculator.getArray();
        
        assertArrayEquals(original, firstCopy);
        assertArrayEquals(original, secondCopy);
        assertNotSame(original, firstCopy);
        assertNotSame(firstCopy, secondCopy);
    }
}