package com.example.demo.requests;

import com.example.demo.dtos.CreateElectionDTO;

import jakarta.validation.constraints.*;

public record ElectionRequest(
    @NotNull @Positive Integer totalVoters,
    @NotNull @Min(0) Integer validVotes,
    @NotNull @Min(0) Integer blankVotes,
    @NotNull @Min(0) Integer nullVotes
) {
    public CreateElectionDTO toDTO() {
        return new CreateElectionDTO(
            totalVoters,
            validVotes,
            blankVotes,
            nullVotes
        );
    }
}