package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    Worker worker;

    @BeforeEach
    void init() {
        worker = new Worker(Colour.RED,0);
        worker.setStartingPosition(1, 2);
    }

    @Test
    @DisplayName("setStartingPositionTest")
    void setStartingPosition() {

        assertAll(
                () -> assertEquals(worker.getCurrentPosition().getX(), 1),
                () -> assertEquals(worker.getCurrentPosition().getY(), 2),
                () -> assertEquals(worker.getCurrentPosition().getZ(), 0)
        );
    }

    @Test
    @DisplayName("movedToPositionTest")
    void movedToPosition() {
        worker.movedToPosition(3, 4, 5);

        assertAll(
                () -> assertEquals(worker.getCurrentPosition().getX(), 3),
                () -> assertEquals(worker.getCurrentPosition().getY(), 4),
                () -> assertEquals(worker.getCurrentPosition().getZ(), 5),
                () -> assertEquals(worker.getPreviousPosition().getX(), 1),
                () -> assertEquals(worker.getPreviousPosition().getY(), 2),
                () -> assertEquals(worker.getPreviousPosition().getZ(), 0)
        );
    }
}
