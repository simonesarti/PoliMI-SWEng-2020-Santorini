package it.polimi.ingsw.model.strategy.losestrategy;

import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class MinotaurLoseTest {

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
    void init() {
        loseStrategy = new MinotaurLose();
        gameBoard = new GameBoard();
        turnInfo = new TurnInfo();

        playerTestInfo = new PlayerInfo("simone", new GregorianCalendar(1998, Calendar.SEPTEMBER, 16));
        player2Info = new PlayerInfo("opponent2", new GregorianCalendar(1990, Calendar.JANUARY, 1));
        player3Info=new PlayerInfo("opponent3",new GregorianCalendar(1990, Calendar.JANUARY, 1));

        playerTest = new Player(playerTestInfo);
        player2 = new Player(player2Info);
        player3=new Player(player3Info);

        playerTest.setColour(Colour.WHITE);
        playerTest.getWorker(0).setStartingPosition(0, 0);
        playerTest.getWorker(1).setStartingPosition(0, 1);
        player2.setColour(Colour.BLUE);
        player2.getWorker(0).setStartingPosition(1, 0);
        player2.getWorker(1).setStartingPosition(1, 1);
        player3.setColour(Colour.GREY);
        player3.getWorker(0).setStartingPosition(2,0);
        player3.getWorker(1).setStartingPosition(2,1);
    }

    @Test
    //should immediately return false
    void alreadyMoved() {
        turnInfo.setHasMoved();
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,1));
    }

    @Test
    void movementLoss(){

        int[][] towers=
                {
                        {4,4,4,4,4},
                        {4,4,4,4,0},
                        {3,1,1,2,4},
                        {4,2,2,4,0},
                        {4,0,4,4,4}
                };

        gameBoard.generateBoard(towers);

        //WT (1,2)
        gameBoard.getTowerCell(1, 2).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(1, 2, 1);

        //WT (2,2)
        gameBoard.getTowerCell(2, 2).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(2, 2, 1);

        //ENEMY W2 (3,2)
        gameBoard.getTowerCell(3,2).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(3,2,2);

        //W2 (0,2)
        gameBoard.getTowerCell(0,2).getFirstNotPieceLevel().setWorker(player2.getWorker(1));
        player2.getWorker(1).movedToPosition(0,2,3);

    //ENEMY W3 (1,3)
        gameBoard.getTowerCell(1,3).getFirstNotPieceLevel().setWorker(player3.getWorker(0));
        player3.getWorker(0).movedToPosition(1,3,2);

    //W3 (1,4)
        gameBoard.getTowerCell(1,4).getFirstNotPieceLevel().setWorker(player3.getWorker(1));
        player3.getWorker(1).movedToPosition(1,4,0);

    // TEST:
        //should both be able to move in (2,3)
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,1));

        turnInfo.activateAthenaPower();
        assertTrue(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
        assertTrue(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,1));
    }

}