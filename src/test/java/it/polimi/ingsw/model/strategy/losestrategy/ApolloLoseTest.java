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

class ApolloLoseTest {

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
        loseStrategy=new ApolloLose();
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
    void noSwapSamePlayer() {

        //GAMEBOARD GENERATION
        int[][] towers=
                {
                        {0,0,0,0,0},
                        {0,3,3,3,0},
                        {0,3,1,3,0},
                        {0,3,0,3,0},
                        {0,3,3,3,0}
                };

        gameBoard.generateBoard(towers);

        //WT (2,2)
        gameBoard.getTowerCell(2, 2).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(2, 2, 1);

        //WT (4,4)
        gameBoard.getTowerCell(2, 3).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(2, 3, 0);

        assertTrue(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));

        turnInfo.activateAthenaPower();
        assertTrue(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
    }

    @Test
    void oneSwapPossibleAndThenNoMore() {

        //GAMEBOARD GENERATION
        int[][] towers=
                {
                        {0,0,0,0,0},
                        {0,3,2,3,0},
                        {0,3,1,3,0},
                        {0,3,0,3,0},
                        {0,3,3,3,0}
                };

        gameBoard.generateBoard(towers);

        //WT (2,2)
        gameBoard.getTowerCell(2, 2).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(2, 2, 1);

        //WT (2,3)
        gameBoard.getTowerCell(2, 3).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(2, 3, 0);

        //ENEMY W2 (2,1)
        gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(2,1,2);

        //TEST:
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
        turnInfo.activateAthenaPower();
        assertTrue(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
    }

    @Test
    void oneSwapAndOneMovePossibleAndThenNoMore() {

        //GAMEBOARD GENERATION
        int[][] towers=
                {
                        {0,0,0,0,0},
                        {0,3,2,3,0},
                        {0,2,1,3,0},
                        {0,3,0,3,0},
                        {0,3,3,3,0}
                };

        gameBoard.generateBoard(towers);

        //WT (2,2)
        gameBoard.getTowerCell(2, 2).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(2, 2, 1);

        //WT (2,3)
        gameBoard.getTowerCell(2, 3).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(2, 3, 0);

        //ENEMY W2 (2,1)
        gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(2,1,2);

        //TEST:
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
        turnInfo.activateAthenaPower();
        assertTrue(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
    }

    @Test
    void oneSwapAndOneMovePossibleAndThenOnlySwapSameLevel() {

        //GAMEBOARD GENERATION
        int[][] towers=
                {
                        {0,0,0,0,0},
                        {0,3,1,3,0},
                        {0,2,1,3,0},
                        {0,3,0,3,0},
                        {0,3,3,3,0}
                };

        gameBoard.generateBoard(towers);

        //WT (2,2)
        gameBoard.getTowerCell(2, 2).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(2, 2, 1);

        //WT (2,3)
        gameBoard.getTowerCell(2, 3).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(2, 3, 0);

        //ENEMY W2 (2,1)
        gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(2,1,1);

        //TEST:
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
        turnInfo.activateAthenaPower();
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
    }

    @Test
    void oneSwapAndOneMovePossibleAndThenOnlySwapLowerLevel() {

        //GAMEBOARD GENERATION
        int[][] towers=
                {
                        {0,0,0,0,0},
                        {0,3,0,3,0},
                        {0,2,1,3,0},
                        {0,3,0,3,0},
                        {0,3,3,3,0}
                };

        gameBoard.generateBoard(towers);

        //WT (2,2)
        gameBoard.getTowerCell(2, 2).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(2, 2, 1);

        //WT (2,3)
        gameBoard.getTowerCell(2, 3).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(2, 3, 0);

        //ENEMY W2 (2,1)
        gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(2,1,0);

        //TEST:
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
        turnInfo.activateAthenaPower();
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
    }

    @Test
    void oneSwapAndOneMovePossibleAndThenOnlySwapTwoLowerLevel() {

        //GAMEBOARD GENERATION
        int[][] towers=
                {
                        {0,0,0,0,0},
                        {0,4,0,4,0},
                        {0,3,2,4,0},
                        {0,4,0,4,0},
                        {0,3,3,3,0}
                };

        gameBoard.generateBoard(towers);

        //WT (2,2)
        gameBoard.getTowerCell(2, 2).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(2, 2, 2);

        //WT (2,3)
        gameBoard.getTowerCell(2, 3).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(2, 3, 0);

        //ENEMY W2 (2,1)
        gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(2,1,0);

        //TEST:
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
        turnInfo.activateAthenaPower();
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
    }


}