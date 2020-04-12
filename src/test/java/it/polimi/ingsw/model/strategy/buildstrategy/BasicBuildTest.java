package it.polimi.ingsw.model.strategy.buildstrategy;
import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.piece.Dome;
import it.polimi.ingsw.model.piece.Level1Block;
import it.polimi.ingsw.model.piece.Level2Block;
import it.polimi.ingsw.model.piece.Level3Block;
import it.polimi.ingsw.model.piece.*;
import it.polimi.ingsw.model.strategy.movestrategy.BasicMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class BasicBuildTest {

    BasicMove basicmove;
    BasicBuild basicbuild;
    GameBoard gameBoard;
    TurnInfo turnInfo;
    Player player;
    String piece;
    Player enemy1Player;
    Player enemy2Player;
    PlayerInfo playerInfo;
    PlayerInfo enemy1Info;
    PlayerInfo enemy2Info;
    int[] buildingTo = new int[2];


    @BeforeEach
    void init(){
        basicmove = new BasicMove();
        
        basicbuild = new BasicBuild();
        playerInfo  =new PlayerInfo("Gianpaolo",new GregorianCalendar(1970, Calendar.JULY, 15));
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

        ///////////////////////////////////////////////GAMEBOARD SETUP//////////////////////////////////////////////////

        gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(2,0).increaseTowerHeight();
        gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(2,0).increaseTowerHeight();

        gameBoard.getTowerCell(3,0).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(3,0).increaseTowerHeight();
        gameBoard.getTowerCell(3,0).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(3,0).increaseTowerHeight();

        gameBoard.getTowerCell(4,0).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(4,0).increaseTowerHeight();
        gameBoard.getTowerCell(4,0).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(4,0).increaseTowerHeight();
        gameBoard.getTowerCell(4,0).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(0));
        enemy1Player.getWorker(0).movedToPosition(4,0,2);

        gameBoard.getTowerCell(4,1).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(4,1).increaseTowerHeight();
        gameBoard.getTowerCell(4,1).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(4,1).increaseTowerHeight();
        gameBoard.getTowerCell(4,1).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(4,1).increaseTowerHeight();

        gameBoard.getTowerCell(1,2).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(1,2).increaseTowerHeight();
        gameBoard.getTowerCell(1,2).getFirstNotPieceLevel().setPiece(new Dome());
        gameBoard.getTowerCell(1,2).increaseTowerHeight();
        gameBoard.getTowerCell(1,2).checkCompletion();


        gameBoard.getTowerCell(3,2).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(3,2).increaseTowerHeight();
        gameBoard.getTowerCell(3,2).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(3,2).increaseTowerHeight();
        gameBoard.getTowerCell(3,2).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(3,2).increaseTowerHeight();

        gameBoard.getTowerCell(4,2).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(4,2).increaseTowerHeight();
        gameBoard.getTowerCell(4,2).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(4,2).increaseTowerHeight();
        gameBoard.getTowerCell(4,2).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(4,2).increaseTowerHeight();
        gameBoard.getTowerCell(4,2).getFirstNotPieceLevel().setPiece(new Dome());
        gameBoard.getTowerCell(4,2).increaseTowerHeight();
        gameBoard.getTowerCell(4,2).checkCompletion();


        gameBoard.getTowerCell(1,3).getFirstNotPieceLevel().setPiece(new Dome());
        gameBoard.getTowerCell(1,3).increaseTowerHeight();
        gameBoard.getTowerCell(1,3).checkCompletion();

        gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(2,3).increaseTowerHeight();

        gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(3,3).increaseTowerHeight();
        gameBoard.getTowerCell(3,3).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(3,3).increaseTowerHeight();

        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(4,3).increaseTowerHeight();
        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().setPiece(new Level2Block());
        gameBoard.getTowerCell(4,3).increaseTowerHeight();
        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().setPiece(new Level3Block());
        gameBoard.getTowerCell(4,3).increaseTowerHeight();
        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(1));
        enemy1Player.getWorker(1).movedToPosition(4,3,3);

        gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setPiece(new Level1Block());
        gameBoard.getTowerCell(2,4).increaseTowerHeight();
        gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(0));
        enemy2Player.getWorker(0).movedToPosition(2,4,1);

        gameBoard.getTowerCell(3,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(1));
        enemy2Player.getWorker(1).movedToPosition(3,4,0);

        return;
    }


    @Test
    void checkBuild (){


        //player must have moved
        turnInfo.setChosenWorker(0);
        buildingTo[0]=1;
        buildingTo[1]=1;

        assertEquals(GameMessage.hasNotMoved, basicbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        turnInfo.turnInfoReset();

        //Player has not already built (alreadyBuilt must be false)
        turnInfo.setChosenWorker(0);
        turnInfo.setHasMoved();
        buildingTo[0]=1;
        buildingTo[1]=1;
        piece = "Block";
        turnInfo.setHasBuilt();
        assertEquals(GameMessage.alreadyBuilt, basicbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        turnInfo.turnInfoReset();

        //x and y must be inside the board
        turnInfo.setChosenWorker(0);
        turnInfo.setHasMoved();
        buildingTo[0]=28;
        buildingTo[1]=1;
        assertEquals(GameMessage.notInGameboard, basicbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //worker must be the same that has moved
        buildingTo[0]=1;
        buildingTo[1]=1;

        turnInfo.setChosenWorker(1);
        assertEquals(GameMessage.notSameThatMoved, basicbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        turnInfo.setChosenWorker(0);

        //workerPosition must not be the building position
        buildingTo[0]=player.getWorker(0).getCurrentPosition().getX();
        buildingTo[1]=player.getWorker(0).getCurrentPosition().getY();
        assertEquals(GameMessage.notTheSame, basicbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //workerPosition must be adjacent to building position
        buildingTo[0]=2;
        buildingTo[1]=0;
        assertEquals(GameMessage.notInSurroundings, basicbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //towerCell must not be completed by a dome
        player.getWorker(0).movedToPosition(0,3,0);
        buildingTo[0]=1;
        buildingTo[1]=3;

        assertEquals(GameMessage.noBuildToCompleteTower, basicbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //the chosen piece must not be a "Block" when tower's height is 3
        piece = "Block";
        player.getWorker(0).movedToPosition(2,2,0);
        buildingTo[0]=3;
        buildingTo[1]=2;
        assertEquals(GameMessage.noBlocksInDome, basicbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //the chosen piece must not be a "Dome" when tower's height is <3
        piece = "Dome";
        buildingTo[0]=2;
        buildingTo[1]=3;
        assertEquals(GameMessage.noDomesInBlock, basicbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //there must not be a worker in the building position
        player.getWorker(0).movedToPosition(1,4,0);
        piece = "Block";
        buildingTo[0]=2;
        buildingTo[1]=4;
        assertEquals(GameMessage.noBuildToOccupiedTower, basicbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));



        //there must be pieces left
        //TODO controllare pezzi rimasti?

        player.getWorker(0).movedToPosition(1,4,0);
        piece = "Block";
        buildingTo[0]=2;
        buildingTo[1]=3;
        //move ok
        assertEquals(GameMessage.buildOK, basicbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));


    }

}