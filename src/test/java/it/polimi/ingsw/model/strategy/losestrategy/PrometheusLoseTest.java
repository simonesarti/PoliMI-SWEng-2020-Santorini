package it.polimi.ingsw.model.strategy.losestrategy;

import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class PrometheusLoseTest {

    LoseStrategy loseStrategy;
    GameBoard gameBoard;
    TurnInfo turnInfo;

    PlayerInfo playerTestInfo;
    PlayerInfo player2Info;

    Player playerTest;
    Player player2;

    @BeforeEach
    void init(){
        loseStrategy=new PrometheusLose();
        gameBoard=new GameBoard();
        turnInfo=new TurnInfo();

        playerTestInfo=new PlayerInfo("simone",new GregorianCalendar(1998, Calendar.SEPTEMBER, 16),3);
        player2Info=new PlayerInfo("opponent2",new GregorianCalendar(1990, Calendar.JANUARY, 1),3);

        playerTest=new Player(playerTestInfo);
        player2=new Player(player2Info);

        playerTest.setColour(Colour.WHITE);
        playerTest.getWorker(0).setStartingPosition(0,0);
        playerTest.getWorker(1).setStartingPosition(0,1);
        player2.setColour(Colour.BLUE);
        player2.getWorker(0).setStartingPosition(1,0);
        player2.getWorker(1).setStartingPosition(1,1);
    }

    @Nested
    class MovementTest{

        //should check all possible movements for both workers using the basic rules
        @Test
        void MovementLossBeforeBuild() {

            //GAMEBOARD GENERATION
            int[][] towers=
                    {
                            {4,3,2,4,0},
                            {4,1,1,2,0},
                            {3,2,3,3,0},
                            {0,0,0,0,0},
                            {0,0,0,0,0}
                    };

            gameBoard.generateBoard(towers);

            //POSITIONING WORKERS

            //WT (1,1)
            gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
            playerTest.getWorker(0).movedToPosition(1,1,1);

            //WT (2,1)
            gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
            playerTest.getWorker(1).movedToPosition(2,1,1);

            //W2 (2,0)
            gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
            player2.getWorker(0).movedToPosition(2,0,2);

            //ENEMY W2 (3,1)
            gameBoard.getTowerCell(3,1).getFirstNotPieceLevel().setWorker(player2.getWorker(1));
            player2.getWorker(1).movedToPosition(3,1,2);

            //TEST

            //should be able to move and don't care about the chosen worker
            assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
            assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,1));

            //shouldn't be able to move up to (1,2)
            turnInfo.activateAthenaPower();
            assertTrue(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));
            assertTrue(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,1));

        }


        //should only check the movement of the worker that built previously, ignoring chosenworker passed
        @Test
        void movementLossAfterBuildTrue() {

            //GAMEBOARD GENERATION
            int[][] towers=
                    {
                            {4,3,2,4,0},
                            {4,1,1,2,0},
                            {3,2,3,3,0},
                            {0,0,0,0,0},
                            {0,0,0,0,0}
                    };

            gameBoard.generateBoard(towers);

            //POSITIONING WORKERS

            //WT (1,1)
            gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
            playerTest.getWorker(0).movedToPosition(1,1,1);

            //WT (2,1)
            gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
            playerTest.getWorker(1).movedToPosition(2,1,1);

            //W2 (2,0)
            gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
            player2.getWorker(0).movedToPosition(2,0,2);

            //ENEMY W2 (3,1)
            gameBoard.getTowerCell(3,1).getFirstNotPieceLevel().setWorker(player2.getWorker(1));
            player2.getWorker(1).movedToPosition(3,1,2);

            turnInfo.setHasBuilt();
            turnInfo.setChosenWorker(1);

            //should not be able to move up and should ignore the chosen worker passed
            assertTrue(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,1));
            assertTrue(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));

        }

        @Test
        void movementLossAfterBuildFalse() {

            //GAMEBOARD GENERATION
            int[][] towers=
                    {
                            {4,3,2,4,0},
                            {4,1,1,2,0},
                            {3,3,3,1,0},
                            {0,0,0,0,0},
                            {0,0,0,0,0}
                    };

            gameBoard.generateBoard(towers);

            //POSITIONING WORKERS

            //WT (1,1)
            gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
            playerTest.getWorker(0).movedToPosition(1,1,1);

            //WT (2,1)
            gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
            playerTest.getWorker(1).movedToPosition(2,1,1);

            //W2 (2,0)
            gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
            player2.getWorker(0).movedToPosition(2,0,2);

            //ENEMY W2 (3,1)
            gameBoard.getTowerCell(3,1).getFirstNotPieceLevel().setWorker(player2.getWorker(1));
            player2.getWorker(1).movedToPosition(3,1,2);

            turnInfo.setHasBuilt();
            turnInfo.setChosenWorker(1);

            //should be able to move on same level and should ignore the chosen worker passed, testing only worker 1
            assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,1));
            assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));

        }

        //should return false immediately because the player has already moved, even if setBuild true
        @Test
        void movementLossAfterMovement(){

            turnInfo.setHasMoved();
            assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,0));

            turnInfo.setHasBuilt();
            assertFalse(loseStrategy.movementLoss(turnInfo,gameBoard,playerTest,1));


        }


    }


    @Nested
    class BuildingTest{

        @BeforeEach
        void init(){
            //GAMEBOARD GENERATION
            int[][] towers=
                    {
                            {0,4,0,0,0},
                            {3,4,0,0,0},
                            {0,0,4,4,4},
                            {0,0,4,0,4},
                            {0,0,3,4,4}
                    };

            gameBoard.generateBoard(towers);

            //POSITIONING WORKERS

            //WT (0,0)
            gameBoard.getTowerCell(0,0).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
            playerTest.getWorker(0).movedToPosition(0,0,0);

            //WT (3,3)
            gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setWorker(playerTest.getWorker(1));
            playerTest.getWorker(1).movedToPosition(3,3,0);

            //W2 (0,1)
            gameBoard.getTowerCell(0,1).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
            player2.getWorker(0).movedToPosition(0,1,3);

        }

        @Test
        void AlreadyBuiltTwice() {
            //should immediately return false
            turnInfo.setHasBuilt();
            turnInfo.addBuild();
            turnInfo.addBuild();

            assertFalse(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,0));
            assertFalse(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,1));

        }

        @Test
        void BuiltAndNotMoved() {
            //should immediately return false
            turnInfo.setHasBuilt();
            turnInfo.addBuild();

            assertFalse(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,0));
            assertFalse(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,1));

        }

        //should not be able to build
        @Test
        void AlreadyMovedNotBuilt(){

            turnInfo.setHasMoved();
            turnInfo.addMove();
            turnInfo.setChosenWorker(0);

            //should only care about worker 0 and test him two times, losing both times, even if worker1 can build
            assertTrue(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,0));
            assertTrue(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,1));

            turnInfo.setChosenWorker(1);
            //should only care about worker 1 and test him two times, building both times
            assertFalse(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,0));
            assertFalse(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,1));
        }

        @Test
        void NotAlreadyMoved(){

            //should test both every times and give
            assertFalse(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,0));
            assertFalse(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,1));

            //ENEMY W2 (3,4)
            gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setWorker(player2.getWorker(1));
            player2.getWorker(1).movedToPosition(2,4,3);

            //now neither of them should be able to build
            assertTrue(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,0));
            assertTrue(loseStrategy.buildingLoss(turnInfo,gameBoard,playerTest,1));

        }
    }

}