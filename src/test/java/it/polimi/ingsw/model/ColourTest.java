package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColourTest {

    @Test
    public void isEnemy(){

        assertTrue(Colour.GREY.isEnemy(Colour.WHITE));
        assertTrue(Colour.GREY.isEnemy(Colour.BLUE));
        assertFalse(Colour.GREY.isEnemy(Colour.GREY));

    }

}