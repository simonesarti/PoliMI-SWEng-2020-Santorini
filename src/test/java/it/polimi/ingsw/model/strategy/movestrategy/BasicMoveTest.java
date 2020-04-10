package it.polimi.ingsw.model.strategy.movestrategy;
import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.piece.Dome;
import it.polimi.ingsw.model.piece.Level1Block;
import it.polimi.ingsw.model.piece.Level2Block;
import it.polimi.ingsw.model.piece.Level3Block;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;


public class BasicMoveTest {

    BasicMove basicmove;
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
        basicmove = new BasicMove();
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
    void checkMove(){

        //alreadyMoved must be false
        movingTo[0]=1;
        movingTo[1]=1;
        basicmove.setAlreadyMoved(true);
        assertEquals(GameMessage.alreadyMoved, basicmove.checkMove(gameBoard,player,0,movingTo));
        basicmove.setAlreadyMoved(false);
        //x and y must be inside the board
        //TODO probabilmente bisogna mettere un try attorno alla checkMove e nella catch mandare il messaggio. perché il metodo crasha subito se gli dai un movingTo outofbounds
        //workerPosition must not be the destination position
        movingTo[0]=player.getWorker(0).getCurrentPosition().getX();
        movingTo[1]=player.getWorker(0).getCurrentPosition().getY();
        assertEquals(GameMessage.notTheSame, basicmove.checkMove(gameBoard,player,0,movingTo));
        //workerPosition must be adjacent to destination position
        movingTo[0]=2;
        movingTo[1]=0;
        assertEquals(GameMessage.notInSurroundings, basicmove.checkMove(gameBoard,player,0,movingTo));
        //towerCell must not be completed by a dome
        player.getWorker(0).movedToPosition(0,3,0);
        movingTo[0]=1;
        movingTo[1]=3;
        assertEquals(GameMessage.noMoveToCompleteTower, basicmove.checkMove(gameBoard,player,0,movingTo));
        //towercell height must be <= (worker height +1)
        player.getWorker(0).movedToPosition(3,1,0);
        movingTo[0]=3;
        movingTo[1]=2;
        assertEquals(GameMessage.noHighJump, basicmove.checkMove(gameBoard,player,0,movingTo));
        //towercell must be empty
        player.getWorker(0).movedToPosition(3,0,2);
        movingTo[0]=4;
        movingTo[1]=0;
        assertEquals(GameMessage.noMovedToOccupiedTower, basicmove.checkMove(gameBoard,player,0,movingTo));
        //if Athena's power is active, worker can not move up
        gameBoard.setAthenaPowerTrue();
        player.getWorker(0).movedToPosition(2,2,0);
        movingTo[0]=2;
        movingTo[1]=3;
        assertEquals(GameMessage.athenaNoMoveUp, basicmove.checkMove(gameBoard,player,0,movingTo));
        gameBoard.setAthenaPowerFalse();
        //move ok
        assertEquals(GameMessage.moveOK, basicmove.checkMove(gameBoard,player,0,movingTo));

    }





}