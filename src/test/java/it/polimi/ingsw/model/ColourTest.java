package it.polimi.ingsw.model;




import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColourTest {

    Colour colour;


    @Test
    public void isEnemy_enemyInput_correctOutput(){


        assertTrue(Colour.GREY.isEnemy(Colour.WHITE));

    }

}