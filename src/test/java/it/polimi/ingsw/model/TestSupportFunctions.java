package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSupportFunctions {

        @Test
        public void baseTurnInfoChecker(TurnInfo turnInfo, boolean hasMoved, int numberOfMoves, boolean hasBuilt, int numberOfBuilds, int chosenWorker, boolean turnCanEnd, boolean turnHasEnded){

            assertEquals(turnInfo.getHasAlreadyMoved(),hasMoved);
            assertEquals(turnInfo.getNumberOfMoves(),numberOfMoves);
            assertEquals(turnInfo.getHasAlreadyBuilt(),hasBuilt);
            assertEquals(turnInfo.getNumberOfBuilds(),numberOfBuilds);
            assertEquals(turnInfo.getChosenWorker(),chosenWorker);
            assertEquals(turnInfo.getTurnCanEnd(),turnCanEnd);
            assertEquals(turnInfo.getTurnHasEnded(),turnHasEnded);

        }
    }

