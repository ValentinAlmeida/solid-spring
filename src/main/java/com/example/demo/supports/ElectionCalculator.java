package com.example.demo.supports;

import com.example.demo.dtos.CreateElectionDTO;
import com.example.demo.properties.ElectionCalculatorProperty;

public final class ElectionCalculator {
    private final ElectionCalculatorProperty properties;

    public static ElectionCalculator create(CreateElectionDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DTO cannot be null");
        }
        
        ElectionCalculatorProperty props = new ElectionCalculatorProperty(
            dto.getTotalVoters(),
            dto.getValidVotes(),
            dto.getBlankVotes(),
            dto.getNullVotes()
        );
        
        return new ElectionCalculator(props);
    }

    private ElectionCalculator(ElectionCalculatorProperty properties) {
        validateInputs(properties);
        this.properties = properties;
    }

    private void validateInputs(ElectionCalculatorProperty props) {
        if (props.getTotalVoters() <= 0) {
            throw new IllegalArgumentException("Total voters must be positive");
        }
        if (props.getValidVotes() < 0 || props.getBlankVotes() < 0 || props.getNullVotes() < 0) {
            throw new IllegalArgumentException("Votes cannot be negative");
        }
        if (props.getValidVotes() + props.getBlankVotes() + props.getNullVotes() > props.getTotalVoters()) {
            throw new IllegalArgumentException("Sum of votes exceeds total voters");
        }
    }

    public double calculateValidVotesPercentage() {
        return calculatePercentage(properties.getValidVotes(), properties.getTotalVoters());
    }

    public double calculateBlankVotesPercentage() {
        return calculatePercentage(properties.getBlankVotes(), properties.getTotalVoters());
    }

    public double calculateNullVotesPercentage() {
        return calculatePercentage(properties.getNullVotes(), properties.getTotalVoters());
    }

    private double calculatePercentage(int partial, int total) {
        return (partial * 100.0) / total;
    }
}