package com.example.demo.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.requests.BubbleSortRequest;
import com.example.demo.supports.BubbleSortCalculator;
import com.example.demo.supports.BubbleSortSerializer;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sort")
public class BubbleSortController {

    @PostMapping("/bubble")
    public String sortArray(@Valid @RequestBody BubbleSortRequest request) {
        return BubbleSortSerializer.serialize(BubbleSortCalculator.create(request.toDTO()));
    }
}