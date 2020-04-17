package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.piece.Dome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class AthenaMoveTest {

    AthenaMove athenaMove;
    TurnInfo turnInfo;
    GameBoard gameBoard;
    Player player;
    Player enemy1Player;
    Player enemy2Player;
    PlayerInfo playerInfo;
    PlayerInfo enemy1Info;
    PlayerInfo enemy2Info;
    int[] movingTo = new int[2];



    @BeforeEach
    void init(){
        athenaMove = new AthenaMove();

        playerInfo  =new PlayerInfo("xXoliTheQueenXx",new GregorianCalendar(1998, Calendar.SEPTEMBER, 9));
        player = new Player(playerInfo);
        player.setColour(Colour.WHITE);
        player.getWorker(0).setStartingPosition(0,0);

        enemy1Info  =new PlayerInfo("enemy1",new GregorianCalendar(2000, Calendar.NOVEMBER, 30));
        enemy1Player = new Player(playerInfo);
        enemy1Player.setColour(Colour.BLUE);
        enemy1Player.getWorker(0).setStartingPosition(0,1);
        enemy1Player.getWorker(1).setStartingPosition(0,2);

        enemy2Info  =new PlayerInfo("enemy2",new GregorianCalendar(1999, Calendar.DECEMBER, 7));
        enemy2Player = new Player(playerInfo);
        enemy2Player.setColour(Colour.GREY);
        enemy2Player.getWorker(0).setStartingPosition(0,3);
        enemy2Player.getWorker(1).setStartingPosition(0,4);

        gameBoard = new GameBoard();

        turnInfo = new TurnInfo();

        //GAMEBOARD GENERATION
        int[][] towers=
                {
                        {0,0,2,2,2},
                        {0,0,0,0,3},
                        {0,1,0,3,4},
                        {0,0,1,2,3},
                        {0,0,1,0,0}
                };

        gameBoard.generateBoard(towers);

        //POSITIONING OTHER DOMES

        gameBoard.getTowerCell(1,2).getFirstNotPieceLevel().setPiece(new Dome());
        gameBoard.getTowerCell(1,2).increaseTowerHeight();
        gameBoard.getTowerCell(1,2).checkCompletion();

        gameBoard.getTowerCell(1,3).getFirstNotPieceLevel().setPiece(new Dome());
        gameBoard.getTowerCell(1,3).increaseTowerHeight();
        gameBoard.getTowerCell(1,3).checkCompletion();

        //POSITIONING WORKERS

        gameBoard.getTowerCell(4,0).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(0));
        enemy1Player.getWorker(1).movedToPosition(4,0,2);


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
        assertEquals(GameMessage.alreadyMoved, athenaMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        turnInfo.turnInfoReset();

        //x and y must be inside the board
        movingTo[0]=28;
        movingTo[1]=1;
        assertEquals(GameMessage.notInGameboard, athenaMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //workerPosition must not be the destination position
        movingTo[0]=player.getWorker(0).getCurrentPosition().getX();
        movingTo[1]=player.getWorker(0).getCurrentPosition().getY();
        assertEquals(GameMessage.notOwnPosition, athenaMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //workerPosition must be adjacent to destination position
        movingTo[0]=2;
        movingTo[1]=0;
        assertEquals(GameMessage.notInSurroundings, athenaMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //towerCell must not be completed by a dome
        player.getWorker(0).movedToPosition(0,3,0);
        movingTo[0]=1;
        movingTo[1]=3;
        assertEquals(GameMessage.noMoveToCompleteTower, athenaMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //towercell height must be <= (worker height +1)
        player.getWorker(0).movedToPosition(3,1,0);
        movingTo[0]=3;
        movingTo[1]=2;
        assertEquals(GameMessage.noHighJump, athenaMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //towercell must be empty
        player.getWorker(0).movedToPosition(3,0,2);
        movingTo[0]=4;
        movingTo[1]=0;
        assertEquals(GameMessage.noMovedToOccupiedTower, athenaMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        //moveOK
        player.getWorker(0).movedToPosition(0,0,0);
        movingTo[0]=0;
        movingTo[1]=1;
        assertEquals(GameMessage.moveOK, athenaMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

    }

    @Test
    void differentScenarios_move_Testing(){

        turnInfo.turnInfoReset();

        //moving from a level1block to another level1block
        gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(2,4,1);
        movingTo[0]=2;
        movingTo[1]=3;
        assertEquals(GameMessage.moveOK, athenaMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        assertEquals(GameMessage.buildRequest, athenaMove.move(turnInfo, gameBoard,player,0,movingTo));
        assertTrue((new Position(2,3,1)).equals(player.getWorker(0).getCurrentPosition()));
        assertNull(gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().getWorker());
        assertEquals(player.getWorker(0),gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().getWorker());
        assertEquals(1, turnInfo.getNumberOfMoves());

        assertFalse(turnInfo.getAthenaPowerActive());
        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getChosenWorker());

        //moving from a level1block to a level2block. Also checking previous position
        turnInfo.turnInfoReset();
        gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(2,3,1);
        movingTo[0]=3;
        movingTo[1]=3;
        assertEquals(GameMessage.moveOK, athenaMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        assertEquals(GameMessage.buildRequest, athenaMove.move(turnInfo, gameBoard,player,0,movingTo));
        assertTrue((new Position(3,3,2)).equals(player.getWorker(0).getCurrentPosition()));
        assertTrue((new Position(2,3,1)).equals(player.getWorker(0).getPreviousPosition()));
        assertNull(gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().getWorker());
        assertEquals(player.getWorker(0),gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().getWorker());

        assertTrue(turnInfo.getAthenaPowerActive());
        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getChosenWorker());



    }


}