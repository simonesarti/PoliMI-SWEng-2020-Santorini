package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColourTest {

    Colour colour;


    @Test
    public void isEnemy_enemyInput_correctOutput(){


        assertTrue(Colour.GREY.isEnemy(Colour.WHITE));

    }

}