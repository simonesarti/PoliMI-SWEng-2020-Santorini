package it.polimi.ingsw.model.strategy.losestrategy;

import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    }

    @Test
    void movementLoss() {

        //GAMEBOARD GENERATION
        int[][] towers=
                {
                        {0,0,4,0,2},
                        {0,0,3,0,3},
                        {0,0,2,2,4},
                        {4,3,3,1,2},
                        {0,2,4,0,4}
                };

        gameBoard.generateBoard(towers);

        //POSITIONING WORKERS

        //WT (3,1)
        gameBoard.getTowerCell(3,1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(3,1,0);

        //WT (0,4)
        gameBoard.getTowerCell(0,4).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(0,4,0);

        //W2 (3,0)
        gameBoard.getTowerCell(3,0).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(3,0,0);

        //ENEMY W2 (3,4)
        gameBoard.getTowerCell(3,4).getFirstNotPieceLevel().setWorker(player2.getWorker(1));
        player2.getWorker(1).movedToPosition(3,4,0);


        //TEST:

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



    @DisplayName("first trapped in Domes and worker, second isn't")
    @Test
    void firstTrapped_secondIsNot() {

        //GAMEBOARD GENERATION
        int[][] towers =
                {
                        {0, 4, 0, 0, 0},
                        {2, 4, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 4, 3},
                        {0, 0, 0, 2, 0}
                };

        gameBoard.generateBoard(towers);

        //POSITIONING WORKERS

        //WT (0,0)
        gameBoard.getTowerCell(0, 0).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(0, 0, 0);

        //WT (4,4)
        gameBoard.getTowerCell(4, 4).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
        playerTest.getWorker(1).movedToPosition(4, 4, 0);

        //ENEMY W2 (0,1)
        gameBoard.getTowerCell(0,1).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(0,1,2);

        //W2 (3,4)
        gameBoard.getTowerCell(3,4).getFirstNotPieceLevel().setWorker(player2.getWorker(1));
        player2.getWorker(1).movedToPosition(3,4,2);


        //TEST:

        //hasn't moved yet, should return false
        assertFalse(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,0));

        //has moved and has not built, can execute, should return false because W0 is trapped
        turnInfo.setHasMoved();
        turnInfo.setChosenWorker(0);
        assertTrue(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,0));

        //has moved and not built, should only care about the chosenworker in Turninfo and ignore the 0 passed, should be able to build
        turnInfo.setChosenWorker(1);
        assertFalse(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,0));

        //now has also Built, it should not execute and return false
        turnInfo.setHasBuilt();
        assertFalse(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,0));

    }

}