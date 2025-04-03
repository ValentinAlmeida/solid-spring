package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParseException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format or data type.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request parameters.");
    }
}
