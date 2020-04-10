package it.polimi.ingsw.model.strategy.movestrategy;
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
    int[] movingTo;


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


        gameBoard.getTowerCell(1,3).getFirstNotPieceLevel().setPiece(new Dome());
        gameBoard.getTowerCell(1,3).increaseTowerHeight();

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



}