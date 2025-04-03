package com.example.demo.supports;

import com.example.demo.dtos.CreateBubbleSortDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BubbleSortValidationTest {

    @Test
    void create_WithNullDTO_ThrowsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            BubbleSortCalculator.create(null);
        });
        
        assertEquals("DTO cannot be null", exception.getMessage());
    }

    @Test
    void create_WithNullArray_ReturnsCalculatorWithEmptyArray() {
        CreateBubbleSortDTO dto = new CreateBubbleSortDTO(null);
        BubbleSortCalculator calculator = BubbleSortCalculator.create(dto);
        
        assertNotNull(calculator);
        assertArrayEquals(new int[0], calculator.getArray());
    }

    @Test
    void getArray_ReturnsDefensiveCopy() {
        int[] original = {5, 3, 1};
        BubbleSortCalculator calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(original.clone()));
        
        int[] firstCopy = calculator.getArray();
        firstCopy[0] = 99;
        
        int[] secondCopy = calculator.getArray();
        assertArrayEquals(new int[]{5, 3, 1}, secondCopy);
    }

    @Test
    void sort_ReturnsNewArrayInstance() {
        int[] original = {3, 1, 2};
        BubbleSortCalculator calculator = BubbleSortCalculator.create(new CreateBubbleSortDTO(original.clone()));
        
        int[] sorted1 = calculator.sort();
        int[] sorted2 = calculator.sort();
        
        assertNotSame(sorted1, sorted2, "Cada chamada a sort() deve retornar uma nova instância");
        assertArrayEquals(new int[]{1, 2, 3}, sorted1);
        assertArrayEquals(new int[]{1, 2, 3}, sorted2);
    }

    @Test
    void sort_DoesNotModifyOriginalArray() {
        int[] original = {3, 1, 2};
        CreateBubbleSortDTO dto = new CreateBubbleSortDTO(original.clone());
        BubbleSortCalculator calculator = BubbleSortCalculator.create(dto);
        
        calculator.sort();
        int[] afterSort = calculator.getArray();
        
        assertArrayEquals(original, afterSort, "O array original não deve ser modificado");
    }
}