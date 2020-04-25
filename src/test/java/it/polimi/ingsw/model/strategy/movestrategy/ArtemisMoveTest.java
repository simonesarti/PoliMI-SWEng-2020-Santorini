package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.supportClasses.TestSupportFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class ArtemisMoveTest {

    int[] movingTo = new int[2];
    ArtemisMove artemisMove = new ArtemisMove();

    TestSupportFunctions testMethods = new TestSupportFunctions();
    PlayerInfo playerInfo  = new PlayerInfo("xXoliTheQueenXx",new GregorianCalendar(1998, Calendar.SEPTEMBER, 9));
    Player player = new Player(playerInfo);
    PlayerInfo enemy1Info  =new PlayerInfo("enemy1",new GregorianCalendar(2000, Calendar.NOVEMBER, 30));
    Player enemy1Player = new Player(playerInfo);
    PlayerInfo enemy2Info  =new PlayerInfo("enemy2",new GregorianCalendar(1999, Calendar.DECEMBER, 7));
    Player enemy2Player = new Player(playerInfo);

    GameBoard gameBoard = new GameBoard();
    TurnInfo turnInfo = new TurnInfo();


    @BeforeEach
    void init(){


        testMethods.drawnGameBoardInit(playerInfo,enemy1Info,enemy2Info,player,enemy1Player,enemy2Player,gameBoard,turnInfo);

        //POSITIONING WORKERS
        gameBoard.getTowerCell(0,0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        enemy1Player.getWorker(0).movedToPosition(0,0,0);

        gameBoard.getTowerCell(4,0).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(0));
        enemy1Player.getWorker(1).movedToPosition(4,0,2);


        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(1));
        enemy1Player.getWorker(1).movedToPosition(4,3,3);


        gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(0));
        enemy2Player.getWorker(0).movedToPosition(2,4,1);

        gameBoard.getTowerCell(3,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(1));
        enemy2Player.getWorker(1).movedToPosition(3,4,0);

    }

    @Test
    void everyCheck_checkMove_Testing(){

        gameBoard.getTowerCell(0,0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(0,0,0);

        //number of moves must be < 2
        movingTo[0]=1;
        movingTo[1]=1;
        turnInfo.addMove();
        turnInfo.addMove();
        assertEquals(GameMessage.alreadyMovedTwice, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        turnInfo.turnInfoReset();

        //x and y must be inside the board
        movingTo[0]=28;
        movingTo[1]=1;
        assertEquals(GameMessage.notInGameBoard, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //if artemis has already moved one time, the destination must not be "previous position" and the worker used must be the same
        player.getWorker(0).movedToPosition(1,1,0);
        gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        movingTo[0]=0;
        movingTo[1]=0;
        turnInfo.addMove();
        turnInfo.setHasMoved();
        turnInfo.setChosenWorker(0);
        assertEquals(GameMessage.ArtemisFirstPosition, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        turnInfo.turnInfoReset();
        player.getWorker(0).movedToPosition(0,0,0);
        gameBoard.getTowerCell(0,0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        //workerPosition must not be the destination position
        movingTo[0]=player.getWorker(0).getCurrentPosition().getX();
        movingTo[1]=player.getWorker(0).getCurrentPosition().getY();
        assertEquals(GameMessage.notOwnPosition, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //workerPosition must be adjacent to destination position
        movingTo[0]=2;
        movingTo[1]=0;
        assertEquals(GameMessage.notInSurroundings, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //towerCell must not be completed by a dome
        player.getWorker(0).movedToPosition(0,3,0);
        movingTo[0]=1;
        movingTo[1]=3;
        assertEquals(GameMessage.noMoveToCompleteTower, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //towercell height must be <= (worker height +1)
        player.getWorker(0).movedToPosition(3,1,0);
        movingTo[0]=3;
        movingTo[1]=2;
        assertEquals(GameMessage.noHighJump, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //towercell must be empty
        player.getWorker(0).movedToPosition(3,0,2);
        movingTo[0]=4;
        movingTo[1]=0;
        assertEquals(GameMessage.noMovedToOccupiedTower, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //if Athena's power is active, worker can not move up
        turnInfo.activateAthenaPower();
        player.getWorker(0).movedToPosition(2,2,0);
        movingTo[0]=2;
        movingTo[1]=3;
        assertEquals(GameMessage.athenaNoMoveUp, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        turnInfo.deactivateAthenaPower();

        //move ok
        assertEquals(GameMessage.moveOK, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

    }

    @Test
    void moveTestFirstMove() {

        assertEquals(0,turnInfo.getNumberOfMoves());
        assertEquals(-1,turnInfo.getChosenWorker());

        movingTo[0]=0;
        movingTo[1]=1;
        String result=artemisMove.move(turnInfo,gameBoard,player,0,movingTo);

        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getChosenWorker());
        assertEquals(GameMessage.moveAgainOrBuild, result);

        //old tower is free
        assertNull(gameBoard.getTowerCell(0,0).getFirstNotPieceLevel().getWorker());
        //worker object is on top ot new tower
        assertEquals(player.getWorker(0),gameBoard.getTowerCell(movingTo[0],movingTo[1]).getFirstNotPieceLevel().getWorker());
        //worker coordinates are correct
        assertAll(
                ()->assertEquals(movingTo[0],player.getWorker(0).getCurrentPosition().getX()),
                ()->assertEquals(movingTo[1],player.getWorker(0).getCurrentPosition().getY()),
                ()->assertEquals(gameBoard.getTowerCell(movingTo[0],movingTo[1]).getTowerHeight(),player.getWorker(0).getCurrentPosition().getZ()),
                ()->assertEquals(0,player.getWorker(0).getPreviousPosition().getX()),
                ()->assertEquals(0,player.getWorker(0).getPreviousPosition().getY()),
                ()->assertEquals(gameBoard.getTowerCell(0,0).getTowerHeight(),player.getWorker(0).getPreviousPosition().getZ())
        );
    }


    @Test
    void moveTestSecondMove() {

        assertEquals(0,turnInfo.getNumberOfMoves());
        assertEquals(-1,turnInfo.getChosenWorker());

        int strartingX = player.getWorker(0).getCurrentPosition().getX();
        int strartingY = player.getWorker(0).getCurrentPosition().getY();

        movingTo[0]=0;
        movingTo[1]=1;
        assertEquals(GameMessage.moveOK, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        String result=artemisMove.move(turnInfo,gameBoard,player,0,movingTo);
        assertEquals(GameMessage.moveAgainOrBuild, result);

        ////////////////////////////////////////////TRYING TO MOVE IN THE FIRST POSITION/////////////////////////////////

        movingTo[0]= strartingX;
        movingTo[1]= strartingY;
        assertEquals(GameMessage.ArtemisFirstPosition, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        ///////////////////////////////////////////MOVING CORRECTLY/////////////////////////////////////////////////////
        movingTo[0]= 0;
        movingTo[1]= 2;
        assertEquals(GameMessage.moveOK, artemisMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        result=artemisMove.move(turnInfo,gameBoard,player,0,movingTo);

        assertEquals(GameMessage.buildRequest, result);

        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(2,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getChosenWorker());
        //old tower is free
        assertNull(gameBoard.getTowerCell(0,1).getFirstNotPieceLevel().getWorker());
        //worker object is on top ot new tower
        assertEquals(player.getWorker(0),gameBoard.getTowerCell(movingTo[0],movingTo[1]).getFirstNotPieceLevel().getWorker());
        //worker coordinates are correct
        assertAll(
                ()->assertEquals(movingTo[0],player.getWorker(0).getCurrentPosition().getX()),
                ()->assertEquals(movingTo[1],player.getWorker(0).getCurrentPosition().getY()),
                ()->assertEquals(gameBoard.getTowerCell(movingTo[0],movingTo[1]).getTowerHeight(),player.getWorker(0).getCurrentPosition().getZ()),
                ()->assertEquals(0,player.getWorker(0).getPreviousPosition().getX()),
                ()->assertEquals(1,player.getWorker(0).getPreviousPosition().getY()),
                ()->assertEquals(gameBoard.getTowerCell(0,1).getTowerHeight(),player.getWorker(0).getPreviousPosition().getZ())
        );

    }


}