package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.piece.Dome;
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

        PlayerInfo playerInfo=new PlayerInfo("enemy1",new GregorianCalendar(2000, Calendar.NOVEMBER, 30),3);
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

    @Test
    void getBoardState() {

        int[][] towers=
                {
                        {1,2,3,4,0},
                        {0,1,2,4,4},
                        {3,2,0,1,0},
                        {1,3,2,2,1},
                        {0,0,0,0,0}
                };
        gameBoard.generateBoard(towers);

        //add dome in 0,4
        gameBoard.getTowerCell(0,4).getFirstNotPieceLevel().setPiece(new Dome());
        gameBoard.getTowerCell(0,4).increaseTowerHeight();
        gameBoard.getTowerCell(0,4).checkCompletion();

        PlayerInfo playerTestInfo;
        PlayerInfo player2Info;
        PlayerInfo player3Info;

        Player playerTest;
        Player player2;
        Player player3;

        playerTestInfo = new PlayerInfo("simone", new GregorianCalendar(1998, Calendar.SEPTEMBER, 16),3);
        player2Info = new PlayerInfo("opponent2", new GregorianCalendar(1990, Calendar.JANUARY, 1),3);
        player3Info= new PlayerInfo("opponent3",new GregorianCalendar(1990,Calendar.JANUARY,1),3);

        playerTest = new Player(playerTestInfo);
        player2 = new Player(player2Info);
        player3 = new Player(player3Info);

        playerTest.setColour(Colour.WHITE);
        playerTest.getWorker(0).setStartingPosition(0, 0);
        playerTest.getWorker(1).setStartingPosition(0, 1);
        player2.setColour(Colour.BLUE);
        player2.getWorker(0).setStartingPosition(1, 0);
        player2.getWorker(1).setStartingPosition(1, 1);
        player3.setColour(Colour.GREY);
        player3.getWorker(0).setStartingPosition(2, 0);
        player3.getWorker(1).setStartingPosition(2, 1);

        //WT (1,1)
        gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(1,1,1);

        //WT (0,1)
        gameBoard.getTowerCell(0,1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(0,1,0);

        //ENEMY W2 (2,2)
        gameBoard.getTowerCell(2,2).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(2,2,0);

        //ENEMY W2 (3,3)
        gameBoard.getTowerCell(3,2).getFirstNotPieceLevel().setWorker(player2.getWorker(1));
        player2.getWorker(1).movedToPosition(3,2,1);

        //ENEMY W3 (4,3)
        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().setWorker(player3.getWorker(0));
        player3.getWorker(0).movedToPosition(4,3,1);

        //ENEMY W3 (2,1)
        gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setWorker(player3.getWorker(1));
        player3.getWorker(1).movedToPosition(4,4,0);

        BoardState boardState=gameBoard.getBoardState();

        for(int j=0;j<5;j++){
            for(int i=0;i<5;i++){
                System.out.println("("+i+","+j+")");
                System.out.println("Height "+boardState.getTowerState(i,j).getTowerHeight());
                System.out.println("complete "+boardState.getTowerState(i,j).isCompleted());
                System.out.println("w number "+boardState.getTowerState(i,j).getWorkerNumber());
                System.out.println("w colour"+boardState.getTowerState(i,j).getWorkerColour()+"\n");
            }
        }
    }

}