package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardStateTest {

    @Test
    void setTowerStates() {
        BoardState boardState=new BoardState();
        TowerCell towerCell=new TowerCell();
        boardState.setTowerStates(1,3,towerCell);
        assertEquals(0,boardState.getTowerState(1,3).getTowerHeight());
        assertFalse(boardState.getTowerState(1, 3).isCompleted());
        assertEquals(-1,boardState.getTowerState(1,3).getWorkerNumber());
        assertNull(boardState.getTowerState(1, 3).getWorkerColour());

    }
}