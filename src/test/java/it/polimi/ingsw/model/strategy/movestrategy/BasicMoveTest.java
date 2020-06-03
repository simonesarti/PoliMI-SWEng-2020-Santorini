package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.PlayerInfo;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.supportClasses.TestSupportFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;


public class BasicMoveTest {

    BasicMove basicmove = new BasicMove();
    int[] movingTo = new int[2];
    PlayerInfo playerInfo  =new PlayerInfo("xXoliTheQueenXx",new GregorianCalendar(1998, Calendar.SEPTEMBER, 9),3);
    Player player = new Player(playerInfo);
    PlayerInfo enemy1Info  =new PlayerInfo("enemy1",new GregorianCalendar(2000, Calendar.NOVEMBER, 30),3);
    Player enemy1Player = new Player(playerInfo);
    PlayerInfo enemy2Info  =new PlayerInfo("enemy2",new GregorianCalendar(1999, Calendar.DECEMBER, 7),3);
    Player enemy2Player = new Player(playerInfo);

    GameBoard gameBoard = new GameBoard();
    TurnInfo turnInfo = new TurnInfo();
    TestSupportFunctions testMethods = new TestSupportFunctions();



    @BeforeEach
    void init(){

        testMethods.drawnGameBoardInit(playerInfo,enemy1Info,enemy2Info,player,enemy1Player,enemy2Player,gameBoard,turnInfo);


        //POSITIONING WORKERS

        gameBoard.getTowerCell(4,0).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(0));
        enemy1Player.getWorker(0).movedToPosition(4,0,2);


        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(1));
        enemy1Player.getWorker(1).movedToPosition(4,3,3);


        gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(0));
        enemy2Player.getWorker(0).movedToPosition(2,4,1);

        gameBoard.getTowerCell(3,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(1));
        enemy2Player.getWorker(1).movedToPosition(3,4,0);

        return;
    }

    @Test
    void everyCheck_checkMove_Testing(){

        //alreadyMoved must be false
        movingTo[0]=1;
        movingTo[1]=1;
        turnInfo.setHasMoved();
        assertEquals(GameMessage.alreadyMoved, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        turnInfo.turnInfoReset();

        //x and y must be inside the board
        movingTo[0]=28;
        movingTo[1]=1;
        assertEquals(GameMessage.notInGameBoard, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //workerPosition must not be the destination position
        movingTo[0]=player.getWorker(0).getCurrentPosition().getX();
        movingTo[1]=player.getWorker(0).getCurrentPosition().getY();
        assertEquals(GameMessage.notOwnPosition, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //workerPosition must be adjacent to destination position
        movingTo[0]=2;
        movingTo[1]=0;
        assertEquals(GameMessage.notInSurroundings, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //towerCell must not be completed by a dome
        player.getWorker(0).movedToPosition(0,3,0);
        movingTo[0]=1;
        movingTo[1]=3;
        assertEquals(GameMessage.noMoveToCompleteTower, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //towercell height must be <= (worker height +1)
        player.getWorker(0).movedToPosition(3,1,0);
        movingTo[0]=3;
        movingTo[1]=2;
        assertEquals(GameMessage.noHighJump, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //towercell must be empty
        player.getWorker(0).movedToPosition(3,0,2);
        movingTo[0]=4;
        movingTo[1]=0;
        assertEquals(GameMessage.noMovedToOccupiedTower, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //if Athena's power is active, worker can not move up
        turnInfo.activateAthenaPower();
        player.getWorker(0).movedToPosition(2,2,0);
        movingTo[0]=2;
        movingTo[1]=3;
        assertEquals(GameMessage.athenaNoMoveUp, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        turnInfo.deactivateAthenaPower();

        //move ok
        assertEquals(GameMessage.moveOK, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

    }

    @Test
    void otherCases_checkMove_Testing(){

        //towerCell must not be completed by a dome. This time the dome is on a level1block
        player.getWorker(0).movedToPosition(1,1,0);
        movingTo[0]=1;
        movingTo[1]=2;
        assertEquals(GameMessage.noMoveToCompleteTower, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //Can not jump from a level1 to a level3 block
        player.getWorker(0).movedToPosition(2,3,1);
        movingTo[0]=3;
        movingTo[1]=2;
        assertEquals(GameMessage.noHighJump, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //Can not jump from level zero to a level3 block
        player.getWorker(0).movedToPosition(2,2,0);
        movingTo[0]=3;
        movingTo[1]=2;
        assertEquals(GameMessage.noHighJump, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //You can move down from a level2 to a empty towercell
        player.getWorker(0).movedToPosition(3,3,2);
        movingTo[0]=2;
        movingTo[1]=2;
        assertEquals(GameMessage.moveOK, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //You can not move from a level3 to a level2 that is occupied by another worker
        player.getWorker(0).movedToPosition(4,1,3);
        movingTo[0]=4;
        movingTo[1]=0;
        assertEquals(GameMessage.noMovedToOccupiedTower, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //You can not move from a level3 to a level3 that is completed
        player.getWorker(0).movedToPosition(4,1,3);
        movingTo[0]=4;
        movingTo[1]=2;
        assertEquals(GameMessage.noMoveToCompleteTower, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //if Athena's power is active, worker should be able to move down
        turnInfo.activateAthenaPower();
        player.getWorker(0).movedToPosition(3,3,2);
        movingTo[0]=4;
        movingTo[1]=4;
        assertEquals(GameMessage.moveOK, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        turnInfo.deactivateAthenaPower();


    }

    @Test
    void differentScenarios_move_Testing(){

        turnInfo.turnInfoReset();

        //moving from a level1block to another level1block
        player.getWorker(0).movedToPosition(2,4,1);
        gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        movingTo[0]=2;
        movingTo[1]=3;
        assertEquals(GameMessage.moveOK, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        assertEquals(GameMessage.buildRequest, basicmove.move(turnInfo, gameBoard,player,0,movingTo));
        assertTrue((new Position(2,3,1)).equals(player.getWorker(0).getCurrentPosition()));
        assertNull(gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().getWorker());
        assertEquals(player.getWorker(0),gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().getWorker());

        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getChosenWorker());

        //moving from a level1block to a level3block. Also checking previous position
        turnInfo.turnInfoReset();
        movingTo[0]=3;
        movingTo[1]=3;
        assertEquals(GameMessage.moveOK, basicmove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        assertEquals(GameMessage.buildRequest, basicmove.move(turnInfo, gameBoard,player,0,movingTo));
        assertTrue((new Position(3,3,2)).equals(player.getWorker(0).getCurrentPosition()));
        assertTrue((new Position(2,3,1)).equals(player.getWorker(0).getPreviousPosition()));
        assertNull(gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().getWorker());
        assertEquals(player.getWorker(0),gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().getWorker());

        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getChosenWorker());



    }



}