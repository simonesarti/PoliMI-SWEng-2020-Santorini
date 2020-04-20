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

class MinotaurMoveTest {
    MinotaurMove minotaurMove;
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
        minotaurMove = new MinotaurMove();

        playerInfo  =new PlayerInfo("xXoliTheQueenXx",new GregorianCalendar(1998, Calendar.SEPTEMBER, 9));
        player = new Player(playerInfo);
        player.setColour(Colour.WHITE);
        player.getWorker(0).setStartingPosition(0,0);
        player.getWorker(1).setStartingPosition(0,0);

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
        enemy1Player.getWorker(0).movedToPosition(4,0,2);


        gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(1));
        enemy1Player.getWorker(1).movedToPosition(3,3,2);


        gameBoard.getTowerCell(3,1).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(0));
        enemy2Player.getWorker(0).movedToPosition(3,1,0);

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
        assertEquals(GameMessage.alreadyMoved, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        turnInfo.turnInfoReset();

        //x and y must be inside the board
        movingTo[0]=28;
        movingTo[1]=1;
        assertEquals(GameMessage.notInGameboard, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //workerPosition must not be the destination position
        movingTo[0]=player.getWorker(0).getCurrentPosition().getX();
        movingTo[1]=player.getWorker(0).getCurrentPosition().getY();
        assertEquals(GameMessage.notOwnPosition, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //workerPosition must be adjacent to destination position
        movingTo[0]=2;
        movingTo[1]=0;
        assertEquals(GameMessage.notInSurroundings, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //towerCell must not be completed by a dome
        player.getWorker(0).movedToPosition(0,3,0);
        movingTo[0]=1;
        movingTo[1]=3;
        assertEquals(GameMessage.noMoveToCompleteTower, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //towercell height must be <= (worker height +1)
        player.getWorker(0).movedToPosition(3,1,0);
        movingTo[0]=3;
        movingTo[1]=2;
        assertEquals(GameMessage.noHighJump, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //towercell cannot contain your other worker
        player.getWorker(0).movedToPosition(3,0,2);
        player.getWorker(1).movedToPosition(4,0,2);
        gameBoard.getTowerCell(4,0).getFirstNotPieceLevel().setWorker(player.getWorker(1));
        movingTo[0]=4;
        movingTo[1]=0;
        assertEquals(GameMessage.noMovedToOccupiedTowerApollo, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        //if Athena's power is active, worker can not move up
        turnInfo.activateAthenaPower();
        player.getWorker(0).movedToPosition(2,2,0);
        movingTo[0]=2;
        movingTo[1]=3;
        assertEquals(GameMessage.athenaNoMoveUp, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        turnInfo.deactivateAthenaPower();

        //if towercell is occupied by another player's worker, then if CantBeForcedBackwards is true checkMove returns an error message
        player.getWorker(0).movedToPosition(3,3,2);
        movingTo[0]=3;
        movingTo[1]=4;
        assertEquals(GameMessage.CannotForceWorker, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        //move ok
        player.getWorker(0).movedToPosition(3,4,0);
        movingTo[0]=2;
        movingTo[1]=4;
        assertEquals(GameMessage.moveOK, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

    }

    @Test
    void otherCases_checkMove_Testing() {

        //Can not move because there is a level1block with a dome behind the enemy worker
        gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(0));
        enemy1Player.getWorker(0).movedToPosition(1,1,0);
        gameBoard.getTowerCell(1,0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(1,0,0);
        movingTo[0]=1;
        movingTo[1]=1;
        assertEquals(GameMessage.CannotForceWorker, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        turnInfo.turnInfoReset();



    }

    @Test
    void differentScenarios_move_Testing(){

        //Minotaur using his power. From a level2block to a level0block occupied by enemy2 worker0 that is forced on a level3block

        turnInfo.turnInfoReset();
        gameBoard.getTowerCell(3,0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(3,0,2);
        movingTo[0]=3;
        movingTo[1]=1;
        assertEquals(GameMessage.moveOK, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));

        minotaurMove.move(turnInfo, gameBoard,player,0,movingTo);

        assertTrue((new Position(3,1,0)).equals(player.getWorker(0).getCurrentPosition()));
        assertTrue((new Position(3,0,2)).equals(player.getWorker(0).getPreviousPosition()));
        assertTrue((new Position(3,2,3)).equals(enemy2Player.getWorker(0).getCurrentPosition()));
        assertTrue((new Position(3,1,0)).equals(enemy2Player.getWorker(0).getPreviousPosition()));

        assertEquals(enemy2Player.getWorker(0),gameBoard.getTowerCell(3,2).getFirstNotPieceLevel().getWorker());
        assertEquals(player.getWorker(0),gameBoard.getTowerCell(3,1).getFirstNotPieceLevel().getWorker());

        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getChosenWorker());


        //Minotaur not using his power. From a level3block to a level2block

        turnInfo.turnInfoReset();
        gameBoard.getTowerCell(3,0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(3,0,2);
        movingTo[0]=4;
        movingTo[1]=1;
        assertEquals(GameMessage.moveOK, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        assertEquals(GameMessage.buildRequest, minotaurMove.move(turnInfo, gameBoard,player,0,movingTo));
        assertTrue((new Position(4,1,3)).equals(player.getWorker(0).getCurrentPosition()));
        assertNull(gameBoard.getTowerCell(3,0).getFirstNotPieceLevel().getWorker());
        assertEquals(player.getWorker(0),gameBoard.getTowerCell(4,1).getFirstNotPieceLevel().getWorker());
        assertEquals(1, turnInfo.getNumberOfMoves());

        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getChosenWorker());

        //Minotaur using his power. From a level2block to a levelzero towercell occupied by enemy2 worker1

        turnInfo.turnInfoReset();
        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(4,3,3);
        movingTo[0]=3;
        movingTo[1]=3;
        assertEquals(GameMessage.moveOK, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        assertEquals(GameMessage.buildRequest, minotaurMove.move(turnInfo, gameBoard,player,0,movingTo));
        assertTrue((new Position(3,3,2)).equals(player.getWorker(0).getCurrentPosition()));
        assertTrue((new Position(4,3,3)).equals(player.getWorker(0).getPreviousPosition()));
        assertTrue((new Position(2,3,1)).equals(enemy1Player.getWorker(1).getCurrentPosition()));
        assertTrue((new Position(3,3,2)).equals(enemy1Player.getWorker(1).getPreviousPosition()));
        assertEquals(enemy1Player.getWorker(1),gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().getWorker());
        assertEquals(player.getWorker(0),gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().getWorker());

        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getChosenWorker());

        //Minotaur using his power from a level1block forcing a worker that's on a level2 to a level3 (enemy2 worker1)
        turnInfo.turnInfoReset();
        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().workerMoved();
        gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(2,3,1);
        gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(1));
        enemy2Player.getWorker(1).movedToPosition(3,3,2);
        movingTo[0]=3;
        movingTo[1]=3;
        assertEquals(GameMessage.moveOK, minotaurMove.checkMove(turnInfo, gameBoard,player,0,movingTo));
        assertEquals(GameMessage.buildRequest, minotaurMove.move(turnInfo, gameBoard,player,0,movingTo));
        assertTrue((new Position(3,3,2)).equals(player.getWorker(0).getCurrentPosition()));
        assertTrue((new Position(2,3,1)).equals(player.getWorker(0).getPreviousPosition()));
        assertTrue((new Position(4,3,3)).equals(enemy2Player.getWorker(1).getCurrentPosition()));
        assertTrue((new Position(3,3,2)).equals(enemy2Player.getWorker(1).getPreviousPosition()));
        assertEquals(enemy2Player.getWorker(1),gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().getWorker());
        assertEquals(player.getWorker(0),gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().getWorker());

        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getChosenWorker());


    }
}