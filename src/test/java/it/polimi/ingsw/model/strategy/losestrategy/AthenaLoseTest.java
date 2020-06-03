package it.polimi.ingsw.model.strategy.losestrategy;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.PlayerInfo;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AthenaLoseTest {

    LoseStrategy loseStrategy;
    GameBoard gameBoard;
    TurnInfo turnInfo;

    PlayerInfo playerTestInfo;
    PlayerInfo player2Info;
    PlayerInfo player3Info;

    Player playerTest;
    Player player2;
    Player player3;

    @BeforeEach
    void init(){
        loseStrategy=new AthenaLose();
        gameBoard=new GameBoard();
        turnInfo=new TurnInfo();

        playerTestInfo=new PlayerInfo("simone",new GregorianCalendar(1998, Calendar.SEPTEMBER, 16),3);
        player2Info=new PlayerInfo("opponent2",new GregorianCalendar(1990, Calendar.JANUARY, 1),3);
        player3Info=new PlayerInfo("opponent3",new GregorianCalendar(1990, Calendar.JANUARY, 1),3);

        playerTest=new Player(playerTestInfo);
        player2=new Player(player2Info);
        player3=new Player(player3Info);

        playerTest.setColour(Colour.RED);
        playerTest.getWorker(0).setStartingPosition(0,0);
        playerTest.getWorker(1).setStartingPosition(0,1);
        player2.setColour(Colour.BLUE);
        player2.getWorker(0).setStartingPosition(1,0);
        player2.getWorker(1).setStartingPosition(1,1);
        player3.setColour(Colour.PURPLE);
        player3.getWorker(0).setStartingPosition(2,0);
        player3.getWorker(1).setStartingPosition(2,1);
    }

    @Test
    void ignoringOwnPower() {

        //GAMEBOARD GENERATION
        int[][] towers =
                {
                        {4, 3, 0, 0, 0},
                        {1, 0, 0, 0, 0},
                        {2, 2, 3, 2, 1},
                        {0, 0, 4, 0, 2},
                        {0, 0, 4, 3, 1}
                };

        gameBoard.generateBoard(towers);

        //WT (0,1)
        gameBoard.getTowerCell(0,1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(0,1,1);

        //WT (3,3)
        gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(3,3,0);

        //ENEMY W2 (4,2)
        gameBoard.getTowerCell(4,2).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(4,2,1);

        //ENEMY W2 (4,4)
        gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setWorker(player2.getWorker(1));
        player2.getWorker(1).movedToPosition(4,4,1);

        //ENEMY W3 (1,1)
        gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().setWorker(player3.getWorker(0));
        player3.getWorker(0).movedToPosition(1,1,0);

        //TEST:

        //WT1 should be able to move up in (0,2) or (1,2), both before and after athena power is active
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
        turnInfo.activateAthenaPower();
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));

    }

    @Test
    void goingDown() {

        //GAMEBOARD GENERATION
        int[][] towers =
                {
                        {4, 3, 0, 0, 0},
                        {2, 0, 0, 0, 0},
                        {3, 3, 3, 2, 1},
                        {0, 0, 4, 0, 2},
                        {0, 0, 4, 3, 1}
                };

        gameBoard.generateBoard(towers);

        //WT (0,1)
        gameBoard.getTowerCell(0, 1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(0, 1, 2);

        //WT (3,3)
        gameBoard.getTowerCell(3, 3).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(3, 3, 0);

        //ENEMY W2 (4,2)
        gameBoard.getTowerCell(4, 2).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(4, 2, 1);

        //ENEMY W2 (4,4)
        gameBoard.getTowerCell(4, 4).getFirstNotPieceLevel().setWorker(player2.getWorker(1));
        player2.getWorker(1).movedToPosition(4, 4, 1);

        //TEST:

        //WT1 should be able to go up and down despite power
        assertFalse(loseStrategy.movementLoss(turnInfo, gameBoard, playerTest, 0));
        turnInfo.activateAthenaPower();
        assertFalse(loseStrategy.movementLoss(turnInfo, gameBoard, playerTest, 0));
    }

    @Test
    void lose() {

        //GAMEBOARD GENERATION
        int[][] towers =
                {
                        {4, 3, 0, 0, 0},
                        {1, 3, 0, 0, 0},
                        {4, 3, 3, 2, 2},
                        {0, 0, 4, 0, 2},
                        {0, 0, 4, 3, 2}
                };

        gameBoard.generateBoard(towers);

        //WT (0,1)
        gameBoard.getTowerCell(0, 1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(0, 1, 1);

        //WT (3,3)
        gameBoard.getTowerCell(3, 3).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(3, 3, 0);

        turnInfo.activateAthenaPower();
        assertTrue(loseStrategy.movementLoss(turnInfo, gameBoard, playerTest, 0));
        //power deactivated
        assertFalse(turnInfo.getAthenaPowerActive());
        assertTrue(loseStrategy.movementLoss(turnInfo, gameBoard, playerTest, 1));
    }

}