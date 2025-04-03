package com.example.demo.dtos;

public record CreateElectionDTO(
    int totalVoters,
    int validVotes,
    int blankVotes,
    int nullVotes
) {}