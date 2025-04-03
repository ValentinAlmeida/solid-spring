package com.example.demo.supports;

public class MultiplesSumSerializer {
    
    public static String serialize(MultiplesSumCalculator calculator) {
        return String.format(
            "A soma dos múltiplos de 3 ou 5 abaixo de %d é %d",
            calculator.getLimit(),
            calculator.calculate()
        );
    }
}