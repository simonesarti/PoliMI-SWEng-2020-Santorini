package it.polimi.ingsw.model.strategy.buildstrategy;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.piece.Level3Block;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class PrometheusBuildTest {

    BuildStrategy buildStrategy;
    GameBoard gameBoard;
    TurnInfo turnInfo;
    int[] buildingInto;
    String pieceType;

    PlayerInfo playerTestInfo;
    PlayerInfo player2Info;

    Player playerTest;
    Player player2;

    @BeforeEach
    void init() {
        buildStrategy = new PrometheusBuild();
        gameBoard = new GameBoard();
        turnInfo = new TurnInfo();
        buildingInto=new int[2];

        playerTestInfo = new PlayerInfo("simone", new GregorianCalendar(1998, Calendar.SEPTEMBER, 16));
        player2Info = new PlayerInfo("opponent2", new GregorianCalendar(1990, Calendar.JANUARY, 1));

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
    class checkBuild{

        @Test
        void tooManyBuilds() {
            buildingInto[0]=0;
            buildingInto[1]=0;
            pieceType="Block";
            turnInfo.setHasBuilt();
            turnInfo.addBuild();
            turnInfo.addBuild();
            assertTrue(turnInfo.getHasAlreadyBuilt());
            assertEquals(2,turnInfo.getNumberOfBuilds());
            assertEquals(GameMessage.noBuildMoreThanTwice,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,0,buildingInto,pieceType));
        }

        @Test
        void hasNotMovedButAlreadyBuilt(){
            buildingInto[0]=1;
            buildingInto[1]=2;
            pieceType="Block";
            turnInfo.setHasBuilt();
            turnInfo.addBuild();
            assertTrue(turnInfo.getHasAlreadyBuilt());
            assertEquals(1,turnInfo.getNumberOfBuilds());
            assertFalse(turnInfo.getHasAlreadyMoved());
            assertEquals(0,turnInfo.getNumberOfMoves());
            //built but not moved, cant't build again
            assertEquals(GameMessage.hasNotMoved,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,0,buildingInto,pieceType));
        }

        @Test
        void invalidWorkerNumber(){
            buildingInto[0]=1;
            buildingInto[1]=2;
            pieceType="Block";
            assertEquals(GameMessage.invalidWorkerNumber,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,-1,buildingInto,pieceType));
        }

        @Test
        void notInGameboard1(){
            buildingInto[0]=-1;
            buildingInto[1]=4;
            pieceType="Block";
            assertEquals(GameMessage.notInGameboard,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,0,buildingInto,pieceType));
        }

        @Test
        void notInGameboard2(){
            buildingInto[0]=4;
            buildingInto[1]=-1;
            pieceType="Block";
            assertEquals(GameMessage.notInGameboard,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,0,buildingInto,pieceType));
        }

        @Test
        void NotSameWorkerOnlyMove(){
            buildingInto[0]=1;
            buildingInto[1]=2;
            pieceType="Block";
            turnInfo.setHasMoved();
            turnInfo.addMove();
            turnInfo.setChosenWorker(0);
            assertTrue(turnInfo.getHasAlreadyMoved());
            assertEquals(1,turnInfo.getNumberOfMoves());
            assertEquals(GameMessage.NotSameWorker,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,1,buildingInto,pieceType));
        }

        @Test
        void NotSameWorkerMoveAndBuild(){
            buildingInto[0]=1;
            buildingInto[1]=2;
            pieceType="Block";
            turnInfo.setHasMoved();
            turnInfo.addMove();
            turnInfo.setChosenWorker(0);
            turnInfo.setHasBuilt();
            turnInfo.addBuild();
            assertTrue(turnInfo.getHasAlreadyMoved());
            assertEquals(1,turnInfo.getNumberOfMoves());
            assertTrue(turnInfo.getHasAlreadyBuilt());
            assertEquals(1,turnInfo.getNumberOfBuilds());
            assertEquals(GameMessage.NotSameWorker,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,1,buildingInto,pieceType));
        }

        @Test
        void notOwnPosition(){
            buildingInto[0]=1;
            buildingInto[1]=1;
            pieceType="Block";
            assertEquals(GameMessage.notOwnPosition,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,0,buildingInto,pieceType));
        }

        @Test
        void notInSurroundings(){
            buildingInto[0]=3;
            buildingInto[1]=2;
            pieceType="Block";
            assertEquals(GameMessage.notInSurroundings,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,0,buildingInto,pieceType));
        }

        @Test
        void noBuildToCompleteTower(){
            buildingInto[0]=0;
            buildingInto[1]=0;
            pieceType="Block";
            assertEquals(GameMessage.noBuildToCompleteTower,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,0,buildingInto,pieceType));
        }

        @Test
        void noBuildToOccupiedTower(){
            buildingInto[0]=2;
            buildingInto[1]=1;
            pieceType="Block";
            assertEquals(GameMessage.noBuildToOccupiedTower,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,0,buildingInto,pieceType));
        }

        @Test
        void noBlocksInDome(){
            buildingInto[0]=0;
            buildingInto[1]=2;
            pieceType="Block";
            assertEquals(GameMessage.noBlocksInDome,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,0,buildingInto,pieceType));
        }

        @Test
        void noDomesInBlock(){
            buildingInto[0]=2;
            buildingInto[1]=2;
            pieceType="Dome";
            assertEquals(GameMessage.noDomesInBlock,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,0,buildingInto,pieceType));
        }

        @Test
        void buildOK(){
            buildingInto[0]=2;
            buildingInto[1]=2;
            pieceType="Block";
            assertEquals(GameMessage.buildOK,buildStrategy.checkBuild(turnInfo,gameBoard,playerTest,0,buildingInto,pieceType));
        }
    }

    @Nested
    class buildTest{

        @Test
        void singleBuildAfterMove() {
            buildingInto[0]=1;
            buildingInto[1]=2;
            pieceType="Block";

            turnInfo.setHasMoved();
            turnInfo.addMove();
            turnInfo.setChosenWorker(0);

            //check tower before build
            assertEquals(2,gameBoard.getTowerCell(buildingInto[0],buildingInto[1]).getTowerHeight());
            assertNull(gameBoard.getTowerCell(buildingInto[0],buildingInto[1]).getLevel(2).getPiece());

            assertFalse(turnInfo.getHasAlreadyBuilt());
            assertEquals(0,turnInfo.getNumberOfBuilds());

            //build parameter check
            assertEquals(GameMessage.turnCompleted,buildStrategy.build(turnInfo,gameBoard,playerTest,0,buildingInto,pieceType));
            assertTrue(turnInfo.getTurnCanEnd());
            assertTrue(turnInfo.getTurnHasEnded());
            assertTrue(turnInfo.getHasAlreadyBuilt());
            assertEquals(1,turnInfo.getNumberOfBuilds());


            //check tower after build
            assertEquals(3,gameBoard.getTowerCell(buildingInto[0],buildingInto[1]).getTowerHeight());
            assertTrue(gameBoard.getTowerCell(buildingInto[0],buildingInto[1]).getLevel(2).getPiece() instanceof Level3Block);
        }

        @Test
        void BuildBeforeMove(){
            buildingInto[0]=1;
            buildingInto[1]=2;
            pieceType="Block";

            //check tower before build
            assertEquals(2,gameBoard.getTowerCell(buildingInto[0],buildingInto[1]).getTowerHeight());
            assertNull(gameBoard.getTowerCell(buildingInto[0],buildingInto[1]).getLevel(2).getPiece());

            assertFalse(turnInfo.getHasAlreadyBuilt());
            assertEquals(0,turnInfo.getNumberOfBuilds());
            assertEquals(-1,turnInfo.getChosenWorker());

            //build parameter check
            assertEquals(GameMessage.moveRequest,buildStrategy.build(turnInfo,gameBoard,playerTest,1,buildingInto,pieceType));

            assertFalse(turnInfo.getTurnCanEnd());
            assertFalse(turnInfo.getTurnHasEnded());
            assertEquals(1,turnInfo.getChosenWorker());
            assertTrue(turnInfo.getHasAlreadyBuilt());
            assertEquals(1,turnInfo.getNumberOfBuilds());

            //check tower after build
            assertEquals(3,gameBoard.getTowerCell(buildingInto[0],buildingInto[1]).getTowerHeight());
            assertTrue(gameBoard.getTowerCell(buildingInto[0],buildingInto[1]).getLevel(2).getPiece() instanceof Level3Block);
        }
    }

}