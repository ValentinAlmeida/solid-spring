package com.example.demo.supports;

import com.example.demo.dtos.CreateBubbleSortDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortCalculatorEdgeCasesTest {

    @Test
    void sort_EmptyArray_ReturnsEmptyArray() {
        BubbleSortCalculator calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(new int[]{}));
        assertArrayEquals(new int[]{}, calculator.sort());
    }

    @Test
    void sort_NullArray_ReturnsEmptyArray() {
        BubbleSortCalculator calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(null));
        assertArrayEquals(new int[]{}, calculator.sort());
    }

    @Test
    void sort_SingleElementArray_ReturnsSameArray() {
        BubbleSortCalculator calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(new int[]{42}));
        assertArrayEquals(new int[]{42}, calculator.sort());
    }

    @Test
    void sort_AlreadySortedArray_ReturnsSameArray() {
        BubbleSortCalculator calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(new int[]{1, 2, 3, 4, 5}));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, calculator.sort());
    }

    @Test
    void sort_ReverseSortedArray_ReturnsCorrectlySorted() {
        BubbleSortCalculator calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(new int[]{5, 4, 3, 2, 1}));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, calculator.sort());
    }

    @Test
    void sort_ArrayWithDuplicates_ReturnsCorrectlySorted() {
        BubbleSortCalculator calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(new int[]{2, 1, 2, 3, 1}));
        assertArrayEquals(new int[]{1, 1, 2, 2, 3}, calculator.sort());
    }

    @Test
    void sort_ArrayWithNegativeNumbers_ReturnsCorrectlySorted() {
        BubbleSortCalculator calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(new int[]{-3, -1, -2, 0, 2, 1}));
        assertArrayEquals(new int[]{-3, -2, -1, 0, 1, 2}, calculator.sort());
    }

    @Test
    void sort_ArrayWithExtremeValues_ReturnsCorrectlySorted() {
        BubbleSortCalculator calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(
            new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}));
        assertArrayEquals(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, calculator.sort());
    }

    @Test
    void sort_LargeArray_ReturnsSortedArray() {
        int[] largeArray = new int[10000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = largeArray.length - i - 1;
        }
        
        BubbleSortCalculator calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(largeArray));
        int[] result = calculator.sort();
        
        assertEquals(largeArray.length, result.length);
        for (int i = 0; i < result.length - 1; i++) {
            assertTrue(result[i] <= result[i + 1]);
        }
    }

    @ParameterizedTest
    @MethodSource("provideEdgeCases")
    void sort_EdgeCases_Parametrized(int[] input, int[] expected) {
        BubbleSortCalculator calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(input));
        assertArrayEquals(expected, calculator.sort());
    }

    private static Stream<Object[]> provideEdgeCases() {
        return Stream.of(
            new Object[]{new int[]{}, new int[]{}},
            new Object[]{new int[]{1, 2}, new int[]{1, 2}},
            new Object[]{new int[]{2, 1}, new int[]{1, 2}},
            new Object[]{new int[]{7, 7, 7, 7}, new int[]{7, 7, 7, 7}},
            new Object[]{new int[]{-5, 3, -2, 0, 1}, new int[]{-5, -2, 0, 1, 3}}
        );
    }

    @Test
    void sort_DoesNotModifyOriginalArray() {
        int[] original = {3, 1, 2};
        CreateBubbleSortDTO dto = new CreateBubbleSortDTO(original.clone());
        
        BubbleSortCalculator calculator = BubbleSortCalculator.create(dto);
        calculator.sort();
        
        assertArrayEquals(new int[]{3, 1, 2}, original);
    }
}