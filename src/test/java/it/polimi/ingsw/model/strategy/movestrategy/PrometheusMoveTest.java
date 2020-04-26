package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.GameMessage;
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

class PrometheusMoveTest {

    MoveStrategy moveStrategy;
    GameBoard gameBoard;
    TurnInfo turnInfo;
    int[] movingTo;

    PlayerInfo playerTestInfo;
    PlayerInfo player2Info;

    Player playerTest;
    Player player2;

    @BeforeEach
    void init() {
        moveStrategy = new PrometheusMove();
        gameBoard = new GameBoard();
        turnInfo = new TurnInfo();
        movingTo=new int[2];

        playerTestInfo = new PlayerInfo("simone", new GregorianCalendar(1998, Calendar.SEPTEMBER, 16),3);
        player2Info = new PlayerInfo("opponent2", new GregorianCalendar(1990, Calendar.JANUARY, 1),3);

        playerTest = new Player(playerTestInfo);
        player2 = new Player(player2Info);

        playerTest.setColour(Colour.WHITE);
        playerTest.getWorker(0).setStartingPosition(0, 0);
        playerTest.getWorker(1).setStartingPosition(0, 1);
        player2.setColour(Colour.BLUE);
        player2.getWorker(0).setStartingPosition(1, 0);
        player2.getWorker(1).setStartingPosition(1, 1);

        //GAMEBOARD GENERATION
        int[][] towers=
                {
                        {4,3,2,0,0},
                        {4,1,1,0,0},
                        {3,2,0,3,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0}
                };

        gameBoard.generateBoard(towers);

        //POSITIONING WORKERS

        //WT (1,1)
        gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().setWorker(playerTest.getWorker(0));
        playerTest.getWorker(0).movedToPosition(1,1,1);

        //ENEMY W2 (2,0)
        gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().setWorker(player2.getWorker(0));
        player2.getWorker(0).movedToPosition(2,0,2);

        //ENEMY W2 (2,1)
        gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().setWorker(player2.getWorker(1));
        player2.getWorker(1).movedToPosition(2,1,1);
    }


    @Nested
    class checkMove {

        @Test
        void alreadyMoved(){
            movingTo[0]=0;
            movingTo[1]=0;
            turnInfo.setHasMoved();
            turnInfo.addMove();
            assertEquals(GameMessage.alreadyMoved,moveStrategy.checkMove(turnInfo,gameBoard,playerTest,0,movingTo));
        }

        @Test
        void outsideGameBoard1(){
            movingTo[0]=-1;
            movingTo[1]=5;
            assertEquals(GameMessage.notInGameBoard,moveStrategy.checkMove(turnInfo,gameBoard,playerTest,0,movingTo));
        }

        @Test
        void outsideGameBoard2(){
            movingTo[0]=5;
            movingTo[1]=-1;
            assertEquals(GameMessage.notInGameBoard,moveStrategy.checkMove(turnInfo,gameBoard,playerTest,0,movingTo));
        }

        @Test
        void notSameWorker(){
            movingTo[0]=0;
            movingTo[1]=0;
            turnInfo.setHasBuilt();
            turnInfo.addBuild();
            turnInfo.setChosenWorker(1);
            assertEquals(GameMessage.NotSameWorker, moveStrategy.checkMove(turnInfo,gameBoard,playerTest,0,movingTo));
        }

        @Test
        void notOwnPosition(){
            movingTo[0]=1;
            movingTo[1]=1;
            assertEquals(GameMessage.notOwnPosition,moveStrategy.checkMove(turnInfo,gameBoard,playerTest,0,movingTo));
        }

        @Test
        void notInSurroundings(){
            movingTo[0]=4;
            movingTo[1]=4;
            assertEquals(GameMessage.notInSurroundings, moveStrategy.checkMove(turnInfo,gameBoard,playerTest,0,movingTo));
        }

        @Test
        void noMoveToCompleteTower(){
            movingTo[0]=0;
            movingTo[1]=0;
            assertEquals(GameMessage.noMoveToCompleteTower, moveStrategy.checkMove(turnInfo,gameBoard,playerTest,0,movingTo));
        }

        @Test
        void noHighJump(){
            movingTo[0]=1;
            movingTo[1]=0;
            assertEquals(GameMessage.noHighJump, moveStrategy.checkMove(turnInfo,gameBoard,playerTest,0,movingTo));
        }

        @Test
        void noMovedToOccupiedTower(){
            movingTo[0]=2;
            movingTo[1]=0;
            assertEquals(GameMessage.noMovedToOccupiedTower, moveStrategy.checkMove(turnInfo,gameBoard,playerTest,0,movingTo));
        }

        @Test
        void prometheusNoMoveUp(){
            movingTo[0]=1;
            movingTo[1]=2;
            turnInfo.setHasBuilt();
            turnInfo.addBuild();
            turnInfo.setChosenWorker(0);
            assertEquals(GameMessage.prometheusNoMoveUp, moveStrategy.checkMove(turnInfo,gameBoard,playerTest,0,movingTo));
        }

        @Test
        void athenaNoMoveUp(){
            movingTo[0]=1;
            movingTo[1]=2;
            turnInfo.activateAthenaPower();
            assertEquals(GameMessage.athenaNoMoveUp, moveStrategy.checkMove(turnInfo,gameBoard,playerTest,0,movingTo));
        }

        @Test
        void moveOK(){
            movingTo[0]=2;
            movingTo[1]=2;
            assertEquals(GameMessage.moveOK, moveStrategy.checkMove(turnInfo,gameBoard,playerTest,0,movingTo));
        }

    }

    @Nested
    class move{

        @Test
        void moveTestBeforeBuild(){
            assertEquals(0,turnInfo.getNumberOfMoves());
            assertEquals(-1,turnInfo.getChosenWorker());

            movingTo[0]=2;
            movingTo[1]=2;
            String result=moveStrategy.move(turnInfo,gameBoard,playerTest,0,movingTo);

            assertTrue(turnInfo.getHasAlreadyMoved());
            assertEquals(1,turnInfo.getNumberOfMoves());
            assertEquals(0,turnInfo.getChosenWorker());
            assertEquals(GameMessage.buildRequest,result);

            //old tower is free
            assertNull(gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().getWorker());
            //worker object is on top ot new tower
            assertEquals(playerTest.getWorker(0),gameBoard.getTowerCell(movingTo[0],movingTo[1]).getFirstNotPieceLevel().getWorker());
            //worker coordinates are correct
            assertAll(
                    ()->assertEquals(movingTo[0],playerTest.getWorker(0).getCurrentPosition().getX()),
                    ()->assertEquals(movingTo[1],playerTest.getWorker(0).getCurrentPosition().getY()),
                    ()->assertEquals(gameBoard.getTowerCell(movingTo[0],movingTo[1]).getTowerHeight(),playerTest.getWorker(0).getCurrentPosition().getZ()),
                    ()->assertEquals(1,playerTest.getWorker(0).getPreviousPosition().getX()),
                    ()->assertEquals(1,playerTest.getWorker(0).getPreviousPosition().getY()),
                    ()->assertEquals(gameBoard.getTowerCell(1,1).getTowerHeight(),playerTest.getWorker(0).getPreviousPosition().getZ())
            );

        }

        @Test
        void moveTestAfterBuild(){
            assertEquals(0,turnInfo.getNumberOfMoves());

            movingTo[0]=2;
            movingTo[1]=2;
            turnInfo.setHasBuilt();
            turnInfo.addBuild();
            turnInfo.setChosenWorker(0);
            assertEquals(0,turnInfo.getChosenWorker());
            String result=moveStrategy.move(turnInfo,gameBoard,playerTest,0,movingTo);

            assertTrue(turnInfo.getHasAlreadyMoved());
            assertEquals(1,turnInfo.getNumberOfMoves());
            assertEquals(0,turnInfo.getChosenWorker());
            assertEquals(GameMessage.buildRequest,result);


            //old tower is free
            assertNull(gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().getWorker());
            //worker object is on top of new tower
            assertEquals(playerTest.getWorker(0),gameBoard.getTowerCell(movingTo[0],movingTo[1]).getFirstNotPieceLevel().getWorker());
            //worker coordinates are correct
            assertAll(
                    ()->assertEquals(movingTo[0],playerTest.getWorker(0).getCurrentPosition().getX()),
                    ()->assertEquals(movingTo[1],playerTest.getWorker(0).getCurrentPosition().getY()),
                    ()->assertEquals(gameBoard.getTowerCell(movingTo[0],movingTo[1]).getTowerHeight(),playerTest.getWorker(0).getCurrentPosition().getZ()),
                    ()->assertEquals(1,playerTest.getWorker(0).getPreviousPosition().getX()),
                    ()->assertEquals(1,playerTest.getWorker(0).getPreviousPosition().getY()),
                    ()->assertEquals(gameBoard.getTowerCell(1,1).getTowerHeight(),playerTest.getWorker(0).getPreviousPosition().getZ())
            );
        }
    }
}