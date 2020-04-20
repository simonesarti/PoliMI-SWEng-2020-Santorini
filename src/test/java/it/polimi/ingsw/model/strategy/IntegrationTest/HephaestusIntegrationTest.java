package it.polimi.ingsw.model.strategy.IntegrationTest;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerBuildChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMovementChoice;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.piece.Dome;
import it.polimi.ingsw.model.piece.Level1Block;
import it.polimi.ingsw.model.piece.Level2Block;
import it.polimi.ingsw.model.piece.Level3Block;
import it.polimi.ingsw.model.strategy.buildstrategy.BasicBuild;
import it.polimi.ingsw.model.strategy.buildstrategy.HephaestusBuild;
import it.polimi.ingsw.model.strategy.movestrategy.ApolloMove;
import it.polimi.ingsw.model.strategy.movestrategy.BasicMove;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;


public class HephaestusIntegrationTest {

    GodCard hephaestusCard;
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

        playerInfo  =new PlayerInfo("Gianpaolo",new GregorianCalendar(1970, Calendar.JULY, 15));
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
        String godDataString[] = {"Hephaestus","God Of Blacksmiths", "Simple", "true", "your worker may build one additional block (not dome) on top of your first block"};
        hephaestusCard = new GodCard(godDataString);
        player.setGodCard(hephaestusCard);


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
    void CompleteTurnTesting() {

        //testPlayer's worker0 starting position is (2,0,2)
        gameBoard.getTowerCell(2, 0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(2, 1, 0);

        //Setting the right turn manually
        model.setColour(player.getColour());

        //////////////////////////////////////////MOVING FOR THE FIRST TIME/////////////////////////////

        //creating message that should trigger the controller object (in this case, triggering will be "manual")
        PlayerMovementChoice moveMessage = new PlayerMovementChoice(new View(), player, 0, 2, 2);
        controller.update(moveMessage);

        //Apollo has moved not using his power. Did he move correctly?
        assertTrue((new Position(2, 2, 0)).equals(player.getWorker(0).getCurrentPosition()));
        assertTrue((new Position(2, 1, 0)).equals(player.getWorker(0).getPreviousPosition()));
        assertEquals(player.getWorker(0), gameBoard.getTowerCell(2, 2).getFirstNotPieceLevel().getWorker());
        assertNull(gameBoard.getTowerCell(2, 1).getFirstNotPieceLevel().getWorker());

        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1, turnInfo.getNumberOfMoves());
        assertEquals(0, turnInfo.getChosenWorker());

        ////////////////////////////////////TRYING TO MOVE ANOTHER TIME/////////////////////////////////

        moveMessage = new PlayerMovementChoice(new View(),player,0,2,3);
        controller.update(moveMessage);

        //all parameters must remain the same
        assertTrue((new Position(2, 2, 0)).equals(player.getWorker(0).getCurrentPosition()));
        assertTrue((new Position(2, 1, 0)).equals(player.getWorker(0).getPreviousPosition()));
        assertEquals(player.getWorker(0), gameBoard.getTowerCell(2, 2).getFirstNotPieceLevel().getWorker());
        assertNull(gameBoard.getTowerCell(2, 1).getFirstNotPieceLevel().getWorker());

        assertTrue(turnInfo.getHasAlreadyMoved());
        assertEquals(1, turnInfo.getNumberOfMoves());
        assertEquals(0, turnInfo.getChosenWorker());

        //////////////////////////////////////BUILDING FOR THE FIRST TIME////////////////////////////////

        //creating build message
        PlayerBuildChoice buildMessage = new PlayerBuildChoice(new View(),player,0,2,3,"Block");
        controller.update(buildMessage);

        //Hephaestus has built a level2block with his HephaestusBuild strategy

        //checking that tower's height increased
        assertTrue(gameBoard.getTowerCell(2,3).getTowerHeight()==2);
        //checking that the piece is right
        assertTrue(gameBoard.getTowerCell(2,3).getLevel(1).getPiece() instanceof Level2Block);
        //checking that tower is not completed
        assertTrue(gameBoard.getTowerCell(2,3).isTowerCompleted()==false);
        //checking that hasBuilt is true
        assertTrue(turnInfo.getHasAlreadyBuilt());
        assertTrue(turnInfo.getNumberOfBuilds()==1);
        assertTrue(turnInfo.getTurnCanEnd()==true);
        assertTrue(turnInfo.getLastBuildCoordinates()[0]==2 && turnInfo.getLastBuildCoordinates()[1]==3);

        //////////////////////////////////////BUILDING AGAIN//////////////////////////////////////////

        //creating build message
        buildMessage = new PlayerBuildChoice(new View(),player,0,2,3,"Block");
        controller.update(buildMessage);

        //Hephaestus has built a level3block with his HephaestusBuild strategy

        //checking that tower's height increased
        assertTrue(gameBoard.getTowerCell(2,3).getTowerHeight()==3);
        //checking that the piece is right
        assertTrue(gameBoard.getTowerCell(2,3).getLevel(2).getPiece() instanceof Level3Block);
        //checking that tower is not completed
        assertTrue(gameBoard.getTowerCell(2,3).isTowerCompleted()==false);
        //checking that hasBuilt is true
        assertTrue(turnInfo.getHasAlreadyBuilt());
        assertTrue(turnInfo.getNumberOfBuilds()==2);
        assertTrue(turnInfo.getTurnHasEnded()==true);

    }



    }
