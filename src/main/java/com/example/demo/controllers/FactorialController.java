package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.requests.FactorialRequest;
import com.example.demo.supports.FactorialCalculator;
import com.example.demo.supports.FactorialSerializer;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/math")
public class FactorialController {

    @GetMapping("/factorial")
    public String calculateFactorial(@Valid @RequestBody FactorialRequest request) {
        return FactorialSerializer.serialize(FactorialCalculator.create(request.toDTO()));
    }
}