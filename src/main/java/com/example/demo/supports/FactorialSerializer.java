package com.example.demo.supports;

public class FactorialSerializer {
    
    public static String serialize(FactorialCalculator calculator) {
        return String.format(
            "Factorial of %d is %d",
            calculator.getNumber(),
            calculator.calculate()
        );
    }
}