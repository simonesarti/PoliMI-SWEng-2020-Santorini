package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColourTest {

    @Test
    public void isEnemy(){

        assertTrue(Colour.PURPLE.isEnemy(Colour.RED));
        assertTrue(Colour.PURPLE.isEnemy(Colour.BLUE));
        assertFalse(Colour.PURPLE.isEnemy(Colour.PURPLE));

    }

}