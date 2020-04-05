package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class PositionTest {

    Position position;

    @Before
    public void setUp(){

        position = new Position();
    }

    @Test
    public void adjacent_samePosition(){

        position.setPosition(1,1,1);
        Position otherPosition = new Position(1,1,3);
        assertFalse(position.adjacent(otherPosition));

    }

    @Test
    public void adjacent_NotAdjacent(){

        position.setPosition(1,1,1);
        Position otherPosition = new Position(1,3,3);
        Position anotherPosition = new Position(3,1,3);
        assertFalse(position.adjacent(otherPosition));
        assertFalse(position.adjacent(anotherPosition));

    }

    @Test
    public void adjacent_areAdjacent(){

        position.setPosition(1,2,1);
        Position otherPosition = new Position(0,2,3);
        assertTrue(position.adjacent(otherPosition));

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