package com.example.demo.supports;

public class ElectionSerializer {
    
    public static String serialize(ElectionCalculator calculator, String prefix) {
        return String.format(
            "'valid = %.1f', 'blank = %.1f', 'null = %.1f'",
            calculator.calculateValidVotesPercentage(),
            calculator.calculateBlankVotesPercentage(),
            calculator.calculateNullVotesPercentage()
        );
    }

    public static String serialize(ElectionCalculator calculator) {
        return serialize(calculator, "");
    }
}