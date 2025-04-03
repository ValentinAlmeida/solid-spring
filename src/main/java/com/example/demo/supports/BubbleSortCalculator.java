package com.example.demo.supports;

import com.example.demo.dtos.CreateBubbleSortDTO;
import com.example.demo.properties.BubbleSortProperty;

public final class BubbleSortCalculator {
    private final BubbleSortProperty properties;

    public static BubbleSortCalculator create(CreateBubbleSortDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DTO cannot be null");
        }
        
        BubbleSortProperty props = new BubbleSortProperty(dto.unsortedArray());
        return new BubbleSortCalculator(props);
    }

    private BubbleSortCalculator(BubbleSortProperty properties) {
        this.properties = properties;
    }

    public int[] getArray() {
        return this.properties.getArray();
    }

    public int[] sort() {
        int[] array = properties.getArray();
        int n = array.length;
        
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (array[j] > array[j+1]) {
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        
        return array;
    }
}