package com.example.demo.supports;

import java.util.Arrays;

public class BubbleSortSerializer {
    
    public static String serialize(BubbleSortCalculator service) {
        int[] sortedArray = service.sort();
        return String.format(
            "Original array: %s\nSorted array: %s",
            Arrays.toString(service.getArray()),
            Arrays.toString(sortedArray)
        );
    }
}