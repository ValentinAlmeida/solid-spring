package com.example.demo.supports;

import com.example.demo.dtos.CreateElectionDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ElectionCalculatorValidationTest {
    @Test
    void create_WithZeroTotalVoters_ThrowsException() {
        CreateElectionDTO dto = new CreateElectionDTO(0, 800, 150, 50);
        assertThrows(IllegalArgumentException.class, () -> ElectionCalculator.create(dto));
    }

    @Test
    void create_WithNegativeValidVotes_ThrowsException() {
        CreateElectionDTO dto = new CreateElectionDTO(1000, -1, 150, 50);
        assertThrows(IllegalArgumentException.class, () -> ElectionCalculator.create(dto));
    }

    @Test
    void create_WithNegativeBlankVotes_ThrowsException() {
        CreateElectionDTO dto = new CreateElectionDTO(1000, 800, -1, 50);
        assertThrows(IllegalArgumentException.class, () -> ElectionCalculator.create(dto));
    }

    @Test
    void create_WithNegativeNullVotes_ThrowsException() {
        CreateElectionDTO dto = new CreateElectionDTO(1000, 800, 150, -1);
        assertThrows(IllegalArgumentException.class, () -> ElectionCalculator.create(dto));
    }

    @Test
    void create_WithVotesExceedingTotal_ThrowsException() {
        CreateElectionDTO dto = new CreateElectionDTO(1000, 900, 100, 50);
        assertThrows(IllegalArgumentException.class, () -> ElectionCalculator.create(dto));
    }
}