package com.example.demo.supports;

import com.example.demo.properties.MultiplesSumProperty;

public final class MultiplesSumCalculator {
    private final MultiplesSumProperty properties;

    public static MultiplesSumCalculator create(int limit) {
        return new MultiplesSumCalculator(new MultiplesSumProperty(limit));
    }

    private MultiplesSumCalculator(MultiplesSumProperty properties) {
        this.properties = properties;
    }

    public long calculate() {
        if (properties.getLimit() <= 0) return 0;
        
        long sum = 0;
        for (int i = 3; i < properties.getLimit(); i++) {
            if (i % 3 == 0 || i % 5 == 0) {
                sum += i;
            }
        }
        return sum;
    }

    public int getLimit() {
        return properties.getLimit();
    }
}