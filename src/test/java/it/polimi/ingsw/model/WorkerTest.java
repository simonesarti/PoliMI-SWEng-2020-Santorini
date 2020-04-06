package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    //last worker's position is always in turnMovementList(0)

    @Test
    public void ErasingMovementsMemory(){

        Worker worker = new Worker(Colour.WHITE);
        Position position = new Position(1,1,1);
        worker.movedToPosition(position);
        position.setPosition(2,9,5);
        worker.movedToPosition(position);
        position.setPosition(4,6,1);
        worker.movedToPosition(position);

        assertTrue(worker.getCurrentPosition().equals(position));

        worker.trimMovementHistory();

        assertTrue(worker.getCurrentPosition().equals(position));
        position.setPosition(1,1,2);
        assertFalse(worker.getCurrentPosition().equals(position));


    }

}