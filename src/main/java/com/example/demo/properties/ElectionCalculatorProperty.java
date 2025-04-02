package com.example.demo.properties;

public final class ElectionCalculatorProperty {
    private final int totalVoters;
    private final int validVotes;
    private final int blankVotes;
    private final int nullVotes;

    public ElectionCalculatorProperty(
        int totalVoters, 
        int validVotes, 
        int blankVotes, 
        int nullVotes
    ) {
        this.totalVoters = totalVoters;
        this.validVotes = validVotes;
        this.blankVotes = blankVotes;
        this.nullVotes = nullVotes;
    }

    public int getTotalVoters() { return totalVoters; }
    public int getValidVotes() { return validVotes; }
    public int getBlankVotes() { return blankVotes; }
    public int getNullVotes() { return nullVotes; }
}