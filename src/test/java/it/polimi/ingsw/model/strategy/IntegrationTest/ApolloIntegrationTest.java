package it.polimi.ingsw.model.strategy.IntegrationTest;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerBuildChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMovementChoice;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.piece.Dome;
import it.polimi.ingsw.model.piece.Level1Block;
import it.polimi.ingsw.model.piece.Level2Block;
import it.polimi.ingsw.model.strategy.buildstrategy.BasicBuild;
import it.polimi.ingsw.model.strategy.movestrategy.ApolloMove;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simulating a turn
 */
public class ApolloIntegrationTest {

    GodCard apolloCard;
    Model model;
    Controller controller;
    TurnInfo turnInfo;
    GameBoard gameBoard;


    Player player;
    Player enemy1Player;
    Player enemy2Player;
    PlayerInfo playerInfo;
    PlayerInfo enemy1Info;
    PlayerInfo enemy2Info;




    @BeforeEach
    void init(){

        model = new Model(3);
        controller = new Controller(model);

        gameBoard = model.getGameBoard();
        turnInfo = model.getTurnInfo();

        playerInfo  =new PlayerInfo("xXoliTheQueenXx",new GregorianCalendar(1998, Calendar.SEPTEMBER, 9));
        player = new Player(playerInfo);
        player.setColour(Colour.WHITE);
        player.getWorker(0).setStartingPosition(0,0);
        player.getWorker(1).setStartingPosition(1,0);

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

        //Instancing testPlayer's godcard
        String godDataString[] = {"Apollo","God Of Music", "Simple", "true", "Your worker may move into an opponent worker's..."};
        apolloCard = new GodCard(godDataString);
        player.setGodCard(apolloCard);

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


        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(1));
        enemy1Player.getWorker(1).movedToPosition(4,3,3);


        gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(0));
        enemy2Player.getWorker(0).movedToPosition(2,4,1);

        gameBoard.getTowerCell(3,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(1));
        enemy2Player.getWorker(1).movedToPosition(3,4,0);

        return;
    }

    @Test
    void CompleteTurnTesting(){

        //testPlayer's worker0 strarting position is (2,0,2)
        gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(2,0,2);

        //Setting the right turn manually
        model.setColour(player.getColour());

        //////////////////////////////////////////MOVING FOR THE FIRST TIME/////////////////////////////

        //creating message that should trigger the controller object (in this case, triggering will be "manual")
        PlayerMovementChoice moveMessage = new PlayerMovementChoice(new View(),player,0,3,0);
        controller.update(moveMessage);

        //Apollo has moved not using his power. Did he move correctly?
        assertTrue((new Position(3,0,2)).equals(player.getWorker(0).getCurrentPosition()));
        assertTrue((new Position(2,0,2)).equals(player.getWorker(0).getPreviousPosition()));
        assertEquals(player.getWorker(0),gameBoard.getTowerCell(3,0).getFirstNotPieceLevel().getWorker());
        assertNull(gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().getWorker());

        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getChosenWorker());

        ////////////////////////////////////TRYING TO MOVE ANOTHER TIME/////////////////////////////////

        moveMessage = new PlayerMovementChoice(new View(),player,0,3,1);
        controller.update(moveMessage);

        //all parameters must remain the same
        assertTrue((new Position(3,0,2)).equals(player.getWorker(0).getCurrentPosition()));
        assertTrue((new Position(2,0,2)).equals(player.getWorker(0).getPreviousPosition()));
        assertEquals(player.getWorker(0),gameBoard.getTowerCell(3,0).getFirstNotPieceLevel().getWorker());
        assertNull(gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().getWorker());

        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getChosenWorker());

        //////////////////////////////////////BUILDING FOR THE FIRST TIME////////////////////////////////

        //creating build message
        PlayerBuildChoice buildMessage = new PlayerBuildChoice(new View(),player,0,3,1,"Block");
        controller.update(buildMessage);

        //Apollo has built a level1block with his basicBuild strategy

        //checking that tower's height increased
        assertTrue(gameBoard.getTowerCell(3,1).getTowerHeight()==1);
        //checking that the piece is right
        assertTrue(gameBoard.getTowerCell(3,1).getLevel(0).getPiece() instanceof Level1Block);
        //checking that tower is not completed
        assertTrue(gameBoard.getTowerCell(3,1).isTowerCompleted()==false);
        //checking that hasBuilt is true
        assertTrue(turnInfo.getHasAlreadyBuilt());

        //////////////////////////////////TRYING TO BUILD AGAIN//////////////////////////////////////////

        //creating build message
        buildMessage = new PlayerBuildChoice(new View(),player,0,2,1,"Block");
        controller.update(buildMessage);

        //Apollo has built a level1block with his basicBuild strategy

        //checking that tower's height didn't increase
        assertTrue(gameBoard.getTowerCell(2,1).getTowerHeight()==0);
        //checking that the piece is right
        assertTrue(gameBoard.getTowerCell(2,1).getLevel(0).getPiece() == null);

        //checking that hasAlreadyBuilt is still true
        assertTrue(turnInfo.getHasAlreadyBuilt());
        //checking that hasAlreadyMoved is still true
        assertTrue(turnInfo.getHasAlreadyMoved());

        //TODO
        //1)terminare turno prima di fare ogni cosa
        //2)terminare dopo la mossa
        //3)terminare normalmente
        //2)costruire prima di muovere
        //4)muovere di nuovo dopo aver costruito nonostante il turno dovrebbe essere finito
        //5)controllare che dopo la prima build il turno sia finito
        //6)testare che la verifica di vittoria funzioni
        //7)testare che la vefrifica di sconfitta funzioni

    }
}
