package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GodClassificationTest {

    @Test
    void parseInput() {

        assertEquals(GodClassification.SIMPLE,GodClassification.parseInput("sImPle"));
        assertEquals(GodClassification.ADVANCED,GodClassification.parseInput("aDvanCeD"));
    }
}