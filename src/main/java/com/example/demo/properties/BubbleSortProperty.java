package com.example.demo.properties;

public final class BubbleSortProperty {
    private final int[] array;

    public BubbleSortProperty(int[] array) {
        this.array = array != null ? array.clone() : new int[0];
    }

    public int[] getArray() {
        return array.clone();
    }
}