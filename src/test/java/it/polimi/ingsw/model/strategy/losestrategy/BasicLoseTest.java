package it.polimi.ingsw.model.strategy.losestrategy;

import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.piece.Dome;
import it.polimi.ingsw.model.piece.Level1Block;
import it.polimi.ingsw.model.piece.Level2Block;
import it.polimi.ingsw.model.piece.Level3Block;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class BasicLoseTest {

    LoseStrategy loseStrategy;
    GameBoard gameBoard;
    TurnInfo turnInfo;

    PlayerInfo playerTestInfo;
    PlayerInfo player2Info;

    Player playerTest;
    Player player2;

    @BeforeEach
    void init(){
        loseStrategy=new BasicLose();
        gameBoard=new GameBoard();
        turnInfo=new TurnInfo();

        playerTestInfo=new PlayerInfo("simone",new GregorianCalendar(1998, Calendar.SEPTEMBER, 16));
        player2Info=new PlayerInfo("opponent2",new GregorianCalendar(1990, Calendar.JANUARY, 1));

        playerTest=new Player(playerTestInfo);
        player2=new Player(player2Info);

        playerTest.setColour(Colour.WHITE);
        playerTest.getWorker(0).setStartingPosition(0,0);
        playerTest.getWorker(1).setStartingPosition(0,1);
        player2.setColour(Colour.BLUE);
        player2.getWorker(0).setStartingPosition(1,0);
        player2.getWorker(1).setStartingPosition(1,1);


        /*

        y\x
            No | No | X | W2 | L2
            ---------------------
            No | No | L3| WT | L3
            ---------------------
            No | No | L2 |L2 | D
            ---------------------
            D  | L3 | L3 |   | L2
            ---------------------
            WT | L2 | X | W2 | L1


         */

        //dome (2,0)
        gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(2,0).increaseTowerHeight();
        gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(2,0).increaseTowerHeight();
        gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(2,0).increaseTowerHeight();
        gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().setPiece(new Dome());
        gameBoard.getTowerCell(2,0).increaseTowerHeight();
        gameBoard.getTowerCell(2,0).checkCompletion();

        //W2 (3,0)
        gameBoard.getTowerCell(3,0).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(3,0,0);

        //LV2 (4,0)
        gameBoard.getTowerCell(4,0).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(4,0).increaseTowerHeight();
        gameBoard.getTowerCell(4,0).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(4,0).increaseTowerHeight();

        //LV3 (2,1)
        gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(2,1).increaseTowerHeight();
        gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(2,1).increaseTowerHeight();
        gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(2,1).increaseTowerHeight();

        //WT (3,1)
        gameBoard.getTowerCell(3,1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(3,1,0);

        //LV3 (4,1)
        gameBoard.getTowerCell(4,1).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(4,1).increaseTowerHeight();
        gameBoard.getTowerCell(4,1).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(4,1).increaseTowerHeight();
        gameBoard.getTowerCell(4,1).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(4,1).increaseTowerHeight();

        //LV2 (2,2)
        gameBoard.getTowerCell(2,2).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(2,2).increaseTowerHeight();
        gameBoard.getTowerCell(2,2).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(2,2).increaseTowerHeight();

        //LV2 (3,2)
        gameBoard.getTowerCell(3,2).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(3,2).increaseTowerHeight();
        gameBoard.getTowerCell(3,2).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(3,2).increaseTowerHeight();

        //dome (4,2)
        gameBoard.getTowerCell(4,2).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(4,2).increaseTowerHeight();
        gameBoard.getTowerCell(4,2).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(4,2).increaseTowerHeight();
        gameBoard.getTowerCell(4,2).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(4,2).increaseTowerHeight();
        gameBoard.getTowerCell(4,2).getFirstNotPieceLevel().setPiece(new Dome());
        gameBoard.getTowerCell(4,2).increaseTowerHeight();
        gameBoard.getTowerCell(4,2).checkCompletion();

        //dome (0,3)
        gameBoard.getTowerCell(0,3).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(0,3).increaseTowerHeight();
        gameBoard.getTowerCell(0,3).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(0,3).increaseTowerHeight();
        gameBoard.getTowerCell(0,3).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(0,3).increaseTowerHeight();
        gameBoard.getTowerCell(0,3).getFirstNotPieceLevel().setPiece(new Dome());
        gameBoard.getTowerCell(0,3).increaseTowerHeight();
        gameBoard.getTowerCell(0,3).checkCompletion();

        //LV3 (1,3)
        gameBoard.getTowerCell(1,3).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(1,3).increaseTowerHeight();
        gameBoard.getTowerCell(1,3).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(1,3).increaseTowerHeight();
        gameBoard.getTowerCell(1,3).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(1,3).increaseTowerHeight();

        //LV3 (2,3)
        gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(2,3).increaseTowerHeight();
        gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(2,3).increaseTowerHeight();
        gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(2,3).increaseTowerHeight();

        //LV1 (3,3)
        gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(3,3).increaseTowerHeight();

        //LV2 (4,3)
        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(4,3).increaseTowerHeight();
        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(4,3).increaseTowerHeight();

        //WT (0,4)
        gameBoard.getTowerCell(0,4).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(0,4,0);

        //LV2 (1,4)
        gameBoard.getTowerCell(1,4).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(1,4).increaseTowerHeight();
        gameBoard.getTowerCell(1,4).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(1,4).increaseTowerHeight();

        //dome (2,4)
        gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(2,4).increaseTowerHeight();
        gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(2,4).increaseTowerHeight();
        gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(2,4).increaseTowerHeight();
        gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setPiece(new Dome());
        gameBoard.getTowerCell(2,4).increaseTowerHeight();
        gameBoard.getTowerCell(2,4).checkCompletion();

        //W2 (3,4)
        gameBoard.getTowerCell(3,4).getFirstNotPieceLevel().setWorker(player2.getWorker(1));
        player2.getWorker(1).movedToPosition(3,4,0);

        //dome (4,4)
        gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(4,4).increaseTowerHeight();
        gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(4,4).increaseTowerHeight();
        gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(4,4).increaseTowerHeight();
        gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setPiece(new Dome());
        gameBoard.getTowerCell(4,4).increaseTowerHeight();
        gameBoard.getTowerCell(4,4).checkCompletion();
    }

    @Test
    void movementLoss() {
        //blocked
        assertTrue(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));

        //moved to a level 1 where he can move to level2
        gameBoard.getTowerCell(3,1).getFirstNotPieceLevel().workerMoved();
        gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(3,3,1);
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));

        //activate athenaPower, he can no longer go up on level 2, so he loses
        turnInfo.activateAthenaPower();
        assertTrue(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));

        //deactivate athena power but he has already moved, should not execute and return false
        turnInfo.deactivateAthenaPower();
        turnInfo.setHasMoved();
        assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
    }

    @Test
    void buildingLoss() {
    }
}