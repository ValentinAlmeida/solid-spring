package com.example.demo.supports;

import com.example.demo.dtos.CreateFactorialDTO;
import com.example.demo.properties.FactorialProperty;

public final class FactorialCalculator {
    private final FactorialProperty properties;

    public static FactorialCalculator create(CreateFactorialDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DTO cannot be null");
        }
        
        FactorialProperty props = new FactorialProperty(dto.number());
        return new FactorialCalculator(props);
    }

    public int getNumber() {
        return properties.getNumber();
    }

    private FactorialCalculator(FactorialProperty properties) {
        this.properties = properties;
    }

    public long calculate() {
        return factorial(properties.getNumber());
    }

    private long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
}