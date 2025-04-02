package com.example.demo.requests;

import com.example.demo.dtos.CreateElectionDTO;
import jakarta.validation.constraints.*;

public final class ElectionRequest {
    @NotNull @Positive
    private final Integer totalVoters;
    
    @NotNull @Min(0)
    private final Integer validVotes;
    
    @NotNull @Min(0)
    private final Integer blankVotes;
    
    @NotNull @Min(0)
    private final Integer nullVotes;

    public ElectionRequest(
        Integer totalVoters, 
        Integer validVotes, 
        Integer blankVotes, 
        Integer nullVotes) {
        
        this.totalVoters = totalVoters;
        this.validVotes = validVotes;
        this.blankVotes = blankVotes;
        this.nullVotes = nullVotes;
    }

    public Integer getTotalVoters() {
        return totalVoters;
    }

    public Integer getValidVotes() {
        return validVotes;
    }

    public Integer getBlankVotes() {
        return blankVotes;
    }

    public Integer getNullVotes() {
        return nullVotes;
    }

    public CreateElectionDTO toDTO() {
        return new CreateElectionDTO(
            totalVoters, 
            validVotes, 
            blankVotes, 
            nullVotes
        );
    }
}