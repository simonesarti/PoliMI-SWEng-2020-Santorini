package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColourTest {

    Colour colour;

    @Before
    public void setup(){
        //non serve fare niente perché le enum non si istanziano
    }

    @Test
    public void isEnemy_enemyInput_correctOutput(){


        assertTrue(Colour.GREY.isEnemy(Colour.WHITE));

    }

}