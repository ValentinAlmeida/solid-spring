package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.supports.MultiplesSumCalculator;
import com.example.demo.supports.MultiplesSumSerializer;

@RestController
@RequestMapping("/api/multiples")
public class MultiplesSumController {

    @GetMapping("/sum")
    public String calculateSum(@RequestParam int limit) {
        return MultiplesSumSerializer.serialize(
            MultiplesSumCalculator.create(limit)
        );
    }
}