package it.polimi.ingsw.model.strategy.IntegrationTest;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerBuildChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerEndOfTurnChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMovementChoice;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.piece.Dome;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class AthenaIntegrationTest {

    GodCard athenaCard;
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
        player.getWorker(0).setStartingPosition(3,0);
        player.getWorker(1).setStartingPosition(0,1);

        enemy1Info  =new PlayerInfo("enemy1",new GregorianCalendar(2000, Calendar.NOVEMBER, 30));
        enemy1Player = new Player(playerInfo);
        enemy1Player.setColour(Colour.BLUE);
        enemy1Player.getWorker(0).setStartingPosition(4,1);
        enemy1Player.getWorker(1).setStartingPosition(3,2);

        enemy2Info  =new PlayerInfo("enemy2",new GregorianCalendar(1999, Calendar.DECEMBER, 7));
        enemy2Player = new Player(playerInfo);
        enemy2Player.setColour(Colour.GREY);
        enemy2Player.getWorker(0).setStartingPosition(1,4);
        enemy2Player.getWorker(1).setStartingPosition(4,4);

        //Instancing testPlayer's godcard
        String godDataString[] = {"Athena","God Of...", "Simple", "true", "If your worker moves up..."};
        athenaCard = new GodCard(godDataString);
        player.setGodCard(athenaCard);

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

        //POSITIONING TEST WORKERS
        gameBoard.getTowerCell(2,0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(2,0,2);

        gameBoard.getTowerCell(0,2).getFirstNotPieceLevel().setWorker(player.getWorker(1));
        player.getWorker(1).movedToPosition(0,2,0);

        //POSITIONING ENEMY WORKERS

        gameBoard.getTowerCell(4,0).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(0));
        enemy1Player.getWorker(0).movedToPosition(4,0,2);


        gameBoard.getTowerCell(4,3).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(1));
        enemy1Player.getWorker(1).movedToPosition(4,3,3);


        gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(0));
        enemy2Player.getWorker(0).movedToPosition(2,4,1);

        gameBoard.getTowerCell(3,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(1));
        enemy2Player.getWorker(1).movedToPosition(3,4,0);

        //TURN RESET
        turnInfo.turnInfoReset();

        return;
    }

    ////////////////////////////////////////////COMPLETE TURNS//////////////////////////////////////////////////////////
    //test not needed

    //////////////////////////////////////////FIRST CHOICE//////////////////////////////////////////////////////////////
    @Test
    void EndBeforeEverything(){

        PlayerMessage message=new PlayerEndOfTurnChoice(new View(),player);
        controller.update(message);
        //method returns immediately

        //turnInfo must still have all his initial values
        assertEquals(0,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getNumberOfBuilds());
        assertFalse(turnInfo.getHasAlreadyMoved());
        assertFalse(turnInfo.getHasAlreadyBuilt());
        assertEquals(-1,turnInfo.getChosenWorker());
        assertFalse(turnInfo.getTurnCanEnd());
        assertFalse(turnInfo.getTurnHasEnded());
    }

    @Test
    void BuildBeforeEverything(){

        PlayerMessage message=new PlayerBuildChoice(new View(),player,1,1,1,"Block");
        controller.update(message);

        //turnInfo must still have all his initial values
        assertEquals(0,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getNumberOfBuilds());
        assertFalse(turnInfo.getHasAlreadyMoved());
        assertFalse(turnInfo.getHasAlreadyBuilt());
        assertEquals(-1,turnInfo.getChosenWorker());
        assertFalse(turnInfo.getTurnCanEnd());
        assertFalse(turnInfo.getTurnHasEnded());
    }

    @Test
    void WrongMoveBeforeEverything(){
        PlayerMessage message=new PlayerMovementChoice(new View(),player,0,2,2);
        controller.update(message);
        //invalid move, denied

        //turnInfo must still have all his initial values
        assertEquals(0,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getNumberOfBuilds());
        assertFalse(turnInfo.getHasAlreadyMoved());
        assertFalse(turnInfo.getHasAlreadyBuilt());
        assertEquals(-1,turnInfo.getChosenWorker());
        assertFalse(turnInfo.getTurnCanEnd());
        assertFalse(turnInfo.getTurnHasEnded());
    }

    @Test
    void WrongMoveBeforeEverything2(){
        PlayerMessage message=new PlayerMovementChoice(new View(),player,-1,1,0);
        controller.update(message);
        //invalid move, denied, invalid worker

        //turnInfo must still have all his initial values
        assertEquals(0,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getNumberOfBuilds());
        assertFalse(turnInfo.getHasAlreadyMoved());
        assertFalse(turnInfo.getHasAlreadyBuilt());
        assertEquals(-1,turnInfo.getChosenWorker());
        assertFalse(turnInfo.getTurnCanEnd());
        assertFalse(turnInfo.getTurnHasEnded());
    }

    @Test //ok
    void CorrectMoveAndActivatingAthenaPower(){

        gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(2,3,1);
        PlayerMessage message=new PlayerMovementChoice(new View(),player,0,3,3);
        controller.update(message);

        //turnInfo must have been modified
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getNumberOfBuilds());
        assertTrue(turnInfo.getHasAlreadyMoved());
        assertTrue(turnInfo.getAthenaPowerActive());
        assertFalse(turnInfo.getHasAlreadyBuilt());
        assertEquals(0,turnInfo.getChosenWorker());
        assertFalse(turnInfo.getTurnCanEnd());
        assertFalse(turnInfo.getTurnHasEnded());
    }

    @Test //ok
    void CorrectMoveBeforeEverythingAndWinBasic(){

        gameBoard.getTowerCell(3,0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(3,0,2);
        PlayerMessage message=new PlayerMovementChoice(new View(),player,0,4,1);
        controller.update(message);

        //turnInfo must have been modified
        assertEquals(1,turnInfo.getNumberOfMoves());
        assertEquals(0,turnInfo.getNumberOfBuilds());
        assertTrue(turnInfo.getHasAlreadyMoved());
        assertFalse(turnInfo.getHasAlreadyBuilt());
        assertEquals(0,turnInfo.getChosenWorker());
        assertFalse(turnInfo.getTurnCanEnd());
        assertFalse(turnInfo.getTurnHasEnded());
    }

    /////////////////////////////////////////SECOND CHOICE//////////////////////////////////////////////////////////////
    @Nested
    class SecondChoice {

        @BeforeEach
        void init() {

            //SETS INITIAL STATE
            turnInfo.setChosenWorker(0);
            turnInfo.setHasMoved();
            turnInfo.addMove();

            //player's worker0 is in (2,0,2)

        }

        //move done, only build should be performed

        @Test
        void EndAftertMove() {

            PlayerMessage message=new PlayerEndOfTurnChoice(new View(),player);
            controller.update(message);
            //method returns immediately

            //turnInfo must still have all his initial values
            assertEquals(1,turnInfo.getNumberOfMoves());
            assertEquals(0,turnInfo.getNumberOfBuilds());
            assertTrue(turnInfo.getHasAlreadyMoved());
            assertFalse(turnInfo.getHasAlreadyBuilt());
            assertEquals(0,turnInfo.getChosenWorker());
            assertFalse(turnInfo.getTurnCanEnd());
            assertFalse(turnInfo.getTurnHasEnded());
        }

        @Test
        void MoveAfterMove() {

            PlayerMessage message=new PlayerMovementChoice(new View(),player,0,3,0);
            controller.update(message);
            //method returns because the player has already moved

            //turnInfo must not have been modified since this class's BeforeEach
            assertEquals(1,turnInfo.getNumberOfMoves());
            assertEquals(0,turnInfo.getNumberOfBuilds());
            assertTrue(turnInfo.getHasAlreadyMoved());
            assertFalse(turnInfo.getHasAlreadyBuilt());
            assertEquals(0,turnInfo.getChosenWorker());
            assertFalse(turnInfo.getTurnCanEnd());
            assertFalse(turnInfo.getTurnHasEnded());
        }

        @Test
        void WrongBuildAfterMove() {
            PlayerMessage message=new PlayerBuildChoice(new View(),player,0,3,0,"Dome");
            controller.update(message);
            //every parameter is wrong, should give error for the wrong block

            //turnInfo must not have been modified since this class's BeforeEach
            assertEquals(1,turnInfo.getNumberOfMoves());
            assertEquals(0,turnInfo.getNumberOfBuilds());
            assertTrue(turnInfo.getHasAlreadyMoved());
            assertFalse(turnInfo.getHasAlreadyBuilt());
            assertEquals(0,turnInfo.getChosenWorker());
            assertFalse(turnInfo.getTurnCanEnd());
            assertFalse(turnInfo.getTurnHasEnded());
        }

        @Test
        void WrongBuildAfterMove2() {
            PlayerMessage message=new PlayerBuildChoice(new View(),player,1,3,0,"Block");
            controller.update(message);
            //wrong worker

            //turnInfo must not have been modified since this class's BeforeEach
            assertEquals(1,turnInfo.getNumberOfMoves());
            assertEquals(0,turnInfo.getNumberOfBuilds());
            assertTrue(turnInfo.getHasAlreadyMoved());
            assertFalse(turnInfo.getHasAlreadyBuilt());
            assertEquals(0,turnInfo.getChosenWorker());
            assertFalse(turnInfo.getTurnCanEnd());
            assertFalse(turnInfo.getTurnHasEnded());
        }

        @Test //ok
        void BuildAfterMove() {
            PlayerMessage message=new PlayerBuildChoice(new View(),player,0,3,0,"Block");
            controller.update(message);
            //should work

            //turnInfo must have been updated
            assertEquals(1,turnInfo.getNumberOfMoves());
            assertEquals(1,turnInfo.getNumberOfBuilds());
            assertTrue(turnInfo.getHasAlreadyMoved());
            assertTrue(turnInfo.getHasAlreadyBuilt());
            assertEquals(0,turnInfo.getChosenWorker());
            assertTrue(turnInfo.getTurnCanEnd());
            assertTrue(turnInfo.getTurnHasEnded());
        }
    }

    /////////////////////////////////////////THIRD CHOICE///////////////////////////////////////////////////////////////
    @Nested
    class ThirdChoice {

        @BeforeEach
        void init() {

            //SETS INITIAL STATE
            turnInfo.setChosenWorker(1);
            turnInfo.setHasMoved();
            turnInfo.addMove();
            turnInfo.setHasBuilt();
            turnInfo.addBuild();
            turnInfo.setTurnCanEnd();
            turnInfo.setTurnHasEnded();

            //player's worker1 is in (0,2,0)

        }

        //turn finishes here, only end should be performed

        @Test
        void MoveAfterFinish() {
            PlayerMessage message=new PlayerMovementChoice(new View(),player,1,0,3);
            controller.update(message);
            //can't execute because turn has ended

            //turnInfo Must be the initial one
            assertEquals(1,turnInfo.getNumberOfMoves());
            assertEquals(1,turnInfo.getNumberOfBuilds());
            assertTrue(turnInfo.getHasAlreadyMoved());
            assertTrue(turnInfo.getHasAlreadyBuilt());
            assertEquals(1,turnInfo.getChosenWorker());
            assertTrue(turnInfo.getTurnCanEnd());
            assertTrue(turnInfo.getTurnHasEnded());
        }

        @Test
        void BuildAfterFinish() {
            PlayerMessage message=new PlayerBuildChoice(new View(),player,1,0,3,"Block");
            controller.update(message);
            //can't execute because turn has ended

            //turnInfo Must be the initial one
            assertEquals(1,turnInfo.getNumberOfMoves());
            assertEquals(1,turnInfo.getNumberOfBuilds());
            assertTrue(turnInfo.getHasAlreadyMoved());
            assertTrue(turnInfo.getHasAlreadyBuilt());
            assertEquals(1,turnInfo.getChosenWorker());
            assertTrue(turnInfo.getTurnCanEnd());
            assertTrue(turnInfo.getTurnHasEnded());
        }

        @Test
        void EndAfterFinish() {
            PlayerMessage message=new PlayerEndOfTurnChoice(new View(),player);
            controller.update(message);
            //correct end of turn

            //turnInfo must have been reset
            assertEquals(0,turnInfo.getNumberOfMoves());
            assertEquals(0,turnInfo.getNumberOfBuilds());
            assertFalse(turnInfo.getHasAlreadyMoved());
            assertFalse(turnInfo.getHasAlreadyBuilt());
            assertEquals(-1,turnInfo.getChosenWorker());
            assertFalse(turnInfo.getTurnCanEnd());
            assertFalse(turnInfo.getTurnHasEnded());

            assertEquals(Colour.BLUE,model.getTurn());
        }
    }
}
