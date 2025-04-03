package com.example.demo.supports;

import com.example.demo.dtos.CreateBubbleSortDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import java.util.Random;

class BubbleSortCalculatorPerformanceTest {

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void sort_SmallArray_CompletesInTime() {
        CreateBubbleSortDTO dto = new CreateBubbleSortDTO(new int[]{5, 3, 2, 4, 7, 1, 0, 6});
        
        BubbleSortCalculator calculator = BubbleSortCalculator.create(dto);
        int[] result = calculator.sort();
        
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7}, result);
    }

    @Test
    @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
    void sort_MediumArray_CompletesQuickly() {
        int[] mediumArray = generateRandomArray(1_000);
        CreateBubbleSortDTO dto = new CreateBubbleSortDTO(mediumArray);
        
        BubbleSortCalculator calculator = BubbleSortCalculator.create(dto);
        int[] result = calculator.sort();
        
        assertTrue(isSorted(result), "Array should be sorted");
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void sort_LargeArray_CompletesInTime() {
        int[] largeArray = generateRandomArray(10_000);
        CreateBubbleSortDTO dto = new CreateBubbleSortDTO(largeArray);
        
        BubbleSortCalculator calculator = BubbleSortCalculator.create(dto);
        int[] result = calculator.sort();
        
        assertTrue(isSorted(result), "Array should be sorted");
    }

    private int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    private boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }
}