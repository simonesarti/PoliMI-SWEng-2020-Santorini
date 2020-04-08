package it.polimi.ingsw.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    Position position;

    @BeforeEach
    public void setUp(){

        position = new Position();
    }

    @Test
    public void adjacent_NotAdjacent(){

        position.setPosition(1,1,1);

        assertFalse(position.adjacent(1,3));
        assertFalse(position.adjacent(3,1));

    }

    @Test
    public void adjacent_areAdjacent(){

        position.setPosition(1,2,1);


        assertTrue(position.adjacent(0,2));

    }

    @Test
    public void equals(){

        position.setPosition(1,2,1);
        Position otherPosition = new Position(1,2,1);
        Position anotherPosition = new Position(1,2,2);
        assertTrue(position.equals(otherPosition));
        assertFalse(position.equals(anotherPosition));

    }

}