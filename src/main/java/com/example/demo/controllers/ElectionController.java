package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.requests.ElectionRequest;
import com.example.demo.supports.ElectionCalculator;
import com.example.demo.supports.ElectionSerializer;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    @GetMapping("/results")
    public String getElectionResults(@Valid @RequestBody ElectionRequest request) {
        return ElectionSerializer.serialize(ElectionCalculator.create(request.toDTO()));
    }
}