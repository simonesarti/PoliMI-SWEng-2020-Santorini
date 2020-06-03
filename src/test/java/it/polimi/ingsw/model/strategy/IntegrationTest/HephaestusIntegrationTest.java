package it.polimi.ingsw.model.strategy.IntegrationTest;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.PlayerBuildChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.PlayerEndOfTurnChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.PlayerMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.PlayerMovementChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.BuildData;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.MoveData;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.PlayerInfo;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.piece.Dome;
import it.polimi.ingsw.model.piece.Level1Block;
import it.polimi.ingsw.model.piece.Level2Block;
import it.polimi.ingsw.model.piece.Level3Block;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.ServerSideConnection;
import it.polimi.ingsw.supportClasses.FakeConnection;
import it.polimi.ingsw.supportClasses.TestSupportFunctions;
import it.polimi.ingsw.view.VirtualView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;


public class HephaestusIntegrationTest {

    Server server;
    ServerSideConnection c1;
    ServerSideConnection c2;
    ServerSideConnection c3;

    Player player;
    Player enemy1Player;
    Player enemy2Player;
    PlayerInfo playerInfo;
    PlayerInfo enemy1Info;
    PlayerInfo enemy2Info;
    GodCard hephaestusCard;

    Controller controller;
    Model model;
    ArrayList<VirtualView> virtualViews;
    GameBoard gameBoard;
    TurnInfo turnInfo;

    TestSupportFunctions testSupportFunctions=new TestSupportFunctions();

    @BeforeEach
    void init() {

        try {
            server = new Server();
            c1 = new FakeConnection(new Socket(),server,"c1");
            c2 = new FakeConnection(new Socket(),server,"c2");
            c3 = new FakeConnection(new Socket(),server,"c3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerInfo  =new PlayerInfo("xXoliTheQueenXx",new GregorianCalendar(1998, Calendar.SEPTEMBER, 9),3);
        player = new Player(playerInfo);

        enemy1Info  =new PlayerInfo("enemy1",new GregorianCalendar(2000, Calendar.NOVEMBER, 30),3);
        enemy1Player = new Player(enemy1Info);

        enemy2Info  =new PlayerInfo("enemy2",new GregorianCalendar(1999, Calendar.DECEMBER, 7),3);
        enemy2Player = new Player(enemy2Info);
        
        //Instancing testPlayer's godcard
        String godDataString[] = {"Hephaestus","God Of Blacksmiths", "Simple", "true", "your worker may build one additional block (not dome) on top of your first block"};
        hephaestusCard = new GodCard(godDataString);
        player.setGodCard(hephaestusCard);

        ArrayList<Player> players=new ArrayList<>();
        players.add(player);
        players.add(enemy1Player);
        players.add(enemy2Player);
        ArrayList<ServerSideConnection> connections=new ArrayList<>();
        connections.add(c1);
        connections.add(c2);
        connections.add(c3);

        controller=new Controller(players,connections);

        virtualViews=controller.getVirtualViews();
        model=controller.getModel();
        turnInfo=model.getTurnInfo();
        gameBoard=model.getGameBoard();

        player.getWorker(0).setStartingPosition(3,0);
        player.getWorker(1).setStartingPosition(0,1);

        enemy1Player.getWorker(0).setStartingPosition(0,1);
        enemy1Player.getWorker(1).setStartingPosition(0,2);

        enemy2Player.getWorker(0).setStartingPosition(0,3);
        enemy2Player.getWorker(1).setStartingPosition(0,4);

        //"xXoliTheQueenXx" is the oldest player. but the one i want to test
        model.setColour(Colour.PURPLE);


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

    }

    @AfterEach
    void end(){
        //closing serverSocket
        try {
            server.closeServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void CompleteTurnTesting() {

        //testPlayer's worker0 starting position is (2,0,2)
        gameBoard.getTowerCell(2, 0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
        player.getWorker(0).movedToPosition(2, 1, 0);

        //Setting the right turn manually
        model.setColour(player.getColour());

        //////////////////////////////////////////MOVING FOR THE FIRST TIME/////////////////////////////


        PlayerMovementChoice moveMessage = new PlayerMovementChoice(virtualViews.get(0), player, new MoveData(0, 2, 2));
        controller.update(moveMessage);


        assertEquals((new Position(2, 2, 0)), player.getWorker(0).getCurrentPosition());
        assertEquals((new Position(2, 1, 0)), player.getWorker(0).getPreviousPosition());
        assertEquals(player.getWorker(0), gameBoard.getTowerCell(2, 2).getFirstNotPieceLevel().getWorker());
        assertNull(gameBoard.getTowerCell(2, 1).getFirstNotPieceLevel().getWorker());

        testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );


        ////////////////////////////////////TRYING TO MOVE ANOTHER TIME/////////////////////////////////

        moveMessage = new PlayerMovementChoice(virtualViews.get(0),player,new MoveData(0,2,3));
        controller.update(moveMessage);

        //all parameters must remain the same
        assertEquals((new Position(2, 2, 0)), player.getWorker(0).getCurrentPosition());
        assertEquals((new Position(2, 1, 0)), player.getWorker(0).getPreviousPosition());
        assertEquals(player.getWorker(0), gameBoard.getTowerCell(2, 2).getFirstNotPieceLevel().getWorker());
        assertNull(gameBoard.getTowerCell(2, 1).getFirstNotPieceLevel().getWorker());

        testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );



        //////////////////////////////////////BUILDING FOR THE FIRST TIME////////////////////////////////

        //creating build message
        PlayerBuildChoice buildMessage = new PlayerBuildChoice(virtualViews.get(0),player,new BuildData(0,2,3,"Block"));
        controller.update(buildMessage);

        //Hephaestus has built a level2block with his HephaestusBuild strategy

        testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 1, 0, true, false );

        //checking that tower's height increased
        assertEquals(2, gameBoard.getTowerCell(2, 3).getTowerHeight());
        //checking that the piece is right
        assertTrue(gameBoard.getTowerCell(2,3).getLevel(1).getPiece() instanceof Level2Block);
        //checking that tower is not completed
        assertFalse(gameBoard.getTowerCell(2, 3).isTowerCompleted());
        //checking last build coordinates
        assertTrue(turnInfo.getLastBuildCoordinates()[0]==2 && turnInfo.getLastBuildCoordinates()[1]==3);

        //////////////////////////////////////BUILDING AGAIN//////////////////////////////////////////

        //creating build message
        buildMessage = new PlayerBuildChoice(virtualViews.get(0),player,new BuildData(0,2,3,"Block"));
        controller.update(buildMessage);

        //Hephaestus has built a level3block with his HephaestusBuild strategy

        testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 2, 0, true, true );

        //checking that tower's height increased
        assertEquals(3, gameBoard.getTowerCell(2, 3).getTowerHeight());
        //checking that the piece is right
        assertTrue(gameBoard.getTowerCell(2,3).getLevel(2).getPiece() instanceof Level3Block);
        //checking that tower is not completed
        assertFalse(gameBoard.getTowerCell(2, 3).isTowerCompleted());
        //checking last build coordinates
        assertTrue(turnInfo.getLastBuildCoordinates()[0]==2 && turnInfo.getLastBuildCoordinates()[1]==3);


    }

    @Nested
    class BeforeEverything{

        @Test
        void EndBeforeEverything(){

            PlayerMessage message=new PlayerEndOfTurnChoice(virtualViews.get(0),player);
            controller.update(message);
            //method returns immediately

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo, false, 0, false, 0, -1, false, false );

        }

        @Test
        void BuildBeforeEverything(){

            PlayerMessage message=new PlayerBuildChoice(virtualViews.get(0),player,new BuildData(1,1,1,"Block"));
            controller.update(message);
            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo, false, 0, false, 0, -1, false, false );

        }

        //moving to an invalid cell
        @Test
        void WrongMoveBeforeEverything(){

            PlayerMessage message=new PlayerMovementChoice(virtualViews.get(0),player,new MoveData(0,2,2));
            controller.update(message);
            //invalid move, denied

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo, false, 0, false, 0, -1, false, false );

        }

        //moving with invalid worker number
        @Test
        void WrongMoveBeforeEverything2(){
            PlayerMessage message=new PlayerMovementChoice(virtualViews.get(0),player,new MoveData(-1,1,0));
            controller.update(message);
            //invalid move, denied, invalid worker

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo, false, 0, false, 0, -1, false, false );

        }

        //moving to an occupied space
        @Test
        void WrongMoveBeforeEverything3(){
            player.getWorker(0).movedToPosition(3, 3, 2);
            PlayerMessage message=new PlayerMovementChoice(virtualViews.get(0),player,new MoveData(0,4,3));
            controller.update(message);
            //invalid move, denied, occupied space

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo, false, 0, false, 0, -1, false, false );

        }

        @Test
        void CorrectMoveBeforeEverythingAndWinBasic(){

            gameBoard.getTowerCell(3,0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
            player.getWorker(0).movedToPosition(3,0,2);
            PlayerMessage message=new PlayerMovementChoice(virtualViews.get(0),player,new MoveData(0,4,1));
            controller.update(message);

            //turnInfo must have been modified
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );


        }



    }

    @Nested
    //moved, should only build
    class AfterMove{

        @BeforeEach
        void init() {

            //SETS INITIAL STATE
            turnInfo.setChosenWorker(0);
            turnInfo.setHasMoved();
            turnInfo.addMove();


            //player's worker0 is in (2,0,2)

        }

        @Test
        void EndAfterMove() {

            PlayerMessage message=new PlayerEndOfTurnChoice(virtualViews.get(0),player);
            controller.update(message);
            //method returns immediately

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );

        }

        @Test
        void MoveAfterMove() {

            PlayerMessage message=new PlayerMovementChoice(virtualViews.get(0),player,new MoveData(0,3,0));
            controller.update(message);
            //method returns because the player has already moved

            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );

        }


        //building a dome to a space reserved to a block
        @Test
        void WrongBuildAfterMove() {
            PlayerMessage message=new PlayerBuildChoice(virtualViews.get(0),player,new BuildData(0,3,0,"Dome"));
            controller.update(message);


            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );
        }
        //wrong worker
        @Test
        void WrongBuildAfterMove2() {
            PlayerMessage message=new PlayerBuildChoice(virtualViews.get(0),player,new BuildData(1,3,0,"Block"));
            controller.update(message);
            //wrong worker

            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );

        }
        //not surrounding cell
        @Test
        void WrongBuildAfterMove3() {

            PlayerMessage message=new PlayerBuildChoice(virtualViews.get(0),player,new BuildData(0,2,2,"Block"));
            controller.update(message);
            //wrong worker

            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );
        }

        //building a block to a space reserved to a dome
        @Test
        void WrongBuildAfterMove4() {
            player.getWorker(0).movedToPosition(2, 2, 0);
            PlayerMessage message=new PlayerBuildChoice(virtualViews.get(0),player,new BuildData(0,3,2,"Block"));
            controller.update(message);


            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );
        }

        @Test //ok
        void BuildAfterMove() {

            PlayerMessage message=new PlayerBuildChoice(virtualViews.get(0),player,new BuildData(0,3,0,"Block"));
            controller.update(message);
            //should work

            //turnInfo must have been updated
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 1, 0, true, false );

            //checking that tower's height increased
            assertEquals(3, gameBoard.getTowerCell(3, 0).getTowerHeight());
            //checking that the piece is right
            assertTrue(gameBoard.getTowerCell(3,0).getLevel(2).getPiece() instanceof Level3Block);
            //checking that tower is not completed
            assertFalse(gameBoard.getTowerCell(3, 0).isTowerCompleted());
            //checking last build coordinates
            assertTrue(turnInfo.getLastBuildCoordinates()[0]==3 && turnInfo.getLastBuildCoordinates()[1]==0);

        }

    }

    @Nested
    class AfterBuild {
        //first build done, should only build again or end
        @BeforeEach
        void init() {

            //SETS INITIAL STATE
            turnInfo.setChosenWorker(1);
            turnInfo.setHasMoved();
            turnInfo.addMove();
            turnInfo.setHasBuilt();
            turnInfo.addBuild();
            turnInfo.setTurnCanEnd();
            turnInfo.setLastBuildCoordinates(0,3);


            //player's worker1 is in (0,2,0)

        }

        @Test
        void MoveAfterFirstBuild() {
            PlayerMessage message = new PlayerMovementChoice(virtualViews.get(0), player, new MoveData(1, 0, 3));
            controller.update(message);
            //can't execute because has already built

            //turnInfo Must be the initial one
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 1, 1, true, false);

        }

        @Test
        void EndAfterFirstBuild() {
            PlayerMessage message = new PlayerEndOfTurnChoice(virtualViews.get(0), player);
            controller.update(message);
            //correct end of turn

            //turnInfo must have been reset
            testSupportFunctions.baseTurnInfoChecker(turnInfo, false, 0, false, 0, -1, false, false);

            assertEquals(Colour.RED, model.getTurn());
        }

        @Test
        void BuildAfterBuild() {
            PlayerMessage message = new PlayerBuildChoice(virtualViews.get(0), player, new BuildData(1, 0, 3, "Block"));
            controller.update(message);

            //turnInfo must have been updated
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 2, 1, true, true);

            //checking that tower's height increased
            assertEquals(1, gameBoard.getTowerCell(0, 3).getTowerHeight());
            //checking that the piece is right
            assertTrue(gameBoard.getTowerCell(0,3).getLevel(0).getPiece() instanceof Level1Block);
            //checking that tower is not completed
            assertFalse(gameBoard.getTowerCell(0, 3).isTowerCompleted());
            //checking last build coordinates
            assertTrue(turnInfo.getLastBuildCoordinates()[0]==0 && turnInfo.getLastBuildCoordinates()[1]==3);
        }


        //building to a different cell than the first one
        @Test
        void WrongBuildAfterBuild() {
            PlayerMessage message = new PlayerBuildChoice(virtualViews.get(0), player, new BuildData(1, 1, 1, "Block"));
            controller.update(message);

            //turnInfo must have been updated
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 1, 1, true, false);

        }

        //building a dome
        @Test
        void WrongBuildAfterBuild2() {
            player.getWorker(1).movedToPosition(2, 2, 0);
            turnInfo.setLastBuildCoordinates(3,2);
            PlayerMessage message = new PlayerBuildChoice(virtualViews.get(0), player, new BuildData(1, 3, 2, "Dome"));
            controller.update(message);

            //turnInfo must have been updated
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 1, 1, true, false);

        }
    }

    @Nested
    //second build done, should only end
    class AfterSecondBuild{

        @BeforeEach
        void init() {

            //SETS INITIAL STATE
            turnInfo.setChosenWorker(1);
            turnInfo.setHasMoved();
            turnInfo.addMove();
            turnInfo.setHasBuilt();
            turnInfo.addBuild();
            turnInfo.addBuild();
            turnInfo.setTurnCanEnd();
            turnInfo.setTurnHasEnded();
            turnInfo.setLastBuildCoordinates(0,3);


            //player's worker1 is in (0,2,0)

        }

        @Test
        void MoveAfterSecondBuild() {
            PlayerMessage message = new PlayerMovementChoice(virtualViews.get(0), player, new MoveData(1, 0, 3));
            controller.update(message);
            //can't execute because has already built

            //turnInfo Must be the initial one
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 2, 1, true, true);

        }

        @Test
        void BuildAfterSecondBuild() {
            PlayerMessage message = new PlayerMovementChoice(virtualViews.get(0), player, new MoveData(1, 0, 3));
            controller.update(message);
            //can't execute because has already built

            //turnInfo Must be the initial one
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 2, 1, true, true);

        }

        @Test
        void EndAfterSecondBuild() {
            PlayerMessage message=new PlayerEndOfTurnChoice(virtualViews.get(0),player);
            controller.update(message);
            //correct end of turn

            //turnInfo must have been reset
            testSupportFunctions.baseTurnInfoChecker(turnInfo,false,0,false,0,-1,false,false);

        }

    }




    }

