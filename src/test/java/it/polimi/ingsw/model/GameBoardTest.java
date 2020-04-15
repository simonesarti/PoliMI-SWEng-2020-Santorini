package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.PlayerInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    GameBoard gameBoard;
    @BeforeEach
    void init(){
        gameBoard=new GameBoard();
    }
    @Test
    void generateBoard() {

        /*
                   Y\X| x=0 | X=1 | x=2 | x=3 | x=4
                   --------------------------------
                   y=0|     |  1,0 |     |      |
                   --------------------------------
                   y=1|     |  1,1 |     |      |
                   --------------------------------
                   y=2|     |  1,2 |     |      |
                   --------------------------------
                   y=3|     |  1,3 |     |      |
                   --------------------------------
                   y=4|     |  1,4 |     |      |
                   --------------------------------

                    0=livello vuoto
                    1=lv1
                    2=lv2
                    3=lv3
                    4=cupola
        */

        int[][] towers=
                {
                    {1,2,3,4,0},
                    {0,1,2,3,4},
                    {3,2,4,1,0},
                    {1,3,2,2,1},
                    {0,0,0,0,0}
                };

        gameBoard.generateBoard(towers);

        assertEquals(1,gameBoard.getTowerCell(0,0).getTowerHeight());
        assertEquals(2,gameBoard.getTowerCell(1,0).getTowerHeight());
        assertEquals(3,gameBoard.getTowerCell(2,0).getTowerHeight());
        assertEquals(4,gameBoard.getTowerCell(3,0).getTowerHeight());
        assertEquals(0,gameBoard.getTowerCell(4,0).getTowerHeight());

    }

    @Test
    void cantBeForcedBackwards() {
        int[][] towers=
                {
                        {1,2,3,4,0},
                        {0,1,2,4,4},
                        {3,2,0,1,0},
                        {1,3,2,2,1},
                        {0,0,0,0,0}
                };
        gameBoard.generateBoard(towers);

        //out of gameboard
        assertTrue(gameBoard.cantBeForcedBackwards(1,1,0,0));
        //out of gameboard
        assertTrue(gameBoard.cantBeForcedBackwards(1,1,1,0));
        //out of gameboard
        assertTrue(gameBoard.cantBeForcedBackwards(1,1,2,0));
        //completed tower behind
        assertTrue(gameBoard.cantBeForcedBackwards(1,1,2,1));

        PlayerInfo playerInfo=new PlayerInfo("enemy1",new GregorianCalendar(2000, Calendar.NOVEMBER, 30));
        Player enemy=new Player(playerInfo);
        enemy.setColour(Colour.GREY);
        enemy.getWorker(0).setStartingPosition(0,0);
        gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setWorker(enemy.getWorker(0));
        enemy.getWorker(0).movedToPosition(3,3,2);

        //worker behind
        assertTrue(gameBoard.cantBeForcedBackwards(1,1,2,2));

        //can push
        assertFalse(gameBoard.cantBeForcedBackwards(1,1,1,2));

    }
}