package it.polimi.ingsw.model.strategy.IntegrationTest;
/*
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerBuildChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerEndOfTurnChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMovementChoice;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.piece.Dome;
import it.polimi.ingsw.model.piece.Level3Block;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;


public class AtlasIntegrationTest {

    GodCard atlasCard;
    Model model;
    Controller controller;
    TurnInfo turnInfo;
    GameBoard gameBoard;
    TestSupportFunctions testSupportFunctions;


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
        testSupportFunctions = new TestSupportFunctions();

        playerInfo  =new PlayerInfo("Gianpaolo",new GregorianCalendar(1970, Calendar.JULY, 15));
        player = new Player(playerInfo);
        player.setColour(Colour.WHITE);
        player.getWorker(0).setStartingPosition(3,0);
        player.getWorker(1).setStartingPosition(0,1);



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
        String godDataString[] = {"Atlas","Titan shouldering the heavens", "Simple", "true", "your worker may build a dome at any level"};
        atlasCard = new GodCard(godDataString);
        player.setGodCard(atlasCard);


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

    @Nested
    class BeforeEverything{

        @Test
        void EndBeforeEverything(){

            PlayerMessage message=new PlayerEndOfTurnChoice(new View(),player);
            controller.update(message);
            //method returns immediately

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo, false, 0, false, 0, -1, false, false );

        }

        @Test
        void BuildBeforeEverything(){

            PlayerMessage message=new PlayerBuildChoice(new View(),player,1,1,1,"Block");
            controller.update(message);
            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo, false, 0, false, 0, -1, false, false );

        }

        //moving to an invalid cell
        @Test
        void WrongMoveBeforeEverything(){

            PlayerMessage message=new PlayerMovementChoice(new View(),player,0,2,2);
            controller.update(message);
            //invalid move, denied

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo, false, 0, false, 0, -1, false, false );

        }

        //moving with invalid worker number
        @Test
        void WrongMoveBeforeEverything2(){
            PlayerMessage message=new PlayerMovementChoice(new View(),player,-1,1,0);
            controller.update(message);
            //invalid move, denied, invalid worker

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo, false, 0, false, 0, -1, false, false );

        }

        //moving to an occupied space
        @Test
        void WrongMoveBeforeEverything3(){
            player.getWorker(0).movedToPosition(3, 3, 2);
            PlayerMessage message=new PlayerMovementChoice(new View(),player,0,4,3);
            controller.update(message);
            //invalid move, denied, occupied space

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo, false, 0, false, 0, -1, false, false );

        }

        @Test
        void CorrectMoveBeforeEverythingAndWinBasic(){

            gameBoard.getTowerCell(3,0).getFirstNotPieceLevel().setWorker(player.getWorker(0));
            player.getWorker(0).movedToPosition(3,0,2);
            PlayerMessage message=new PlayerMovementChoice(new View(),player,0,4,1);
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

            PlayerMessage message=new PlayerEndOfTurnChoice(new View(),player);
            controller.update(message);
            //method returns immediately

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );

        }

        @Test
        void MoveAfterMove() {

            PlayerMessage message=new PlayerMovementChoice(new View(),player,0,3,0);
            controller.update(message);
            //method returns because the player has already moved

            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );

        }


        //building a dome to a space reserved to a block: should work
        @Test
        void BuildAfterMove() {
            PlayerMessage message=new PlayerBuildChoice(new View(),player,0,3,0,"Dome");
            controller.update(message);


            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 1, 0, true, true );
            //checking that tower's height increased
            assertTrue(gameBoard.getTowerCell(3,0).getTowerHeight()==3);
            //checking that the piece is right
            assertTrue(gameBoard.getTowerCell(3,0).getLevel(2).getPiece() instanceof Dome);
            //checking that tower is not completed
            assertTrue(gameBoard.getTowerCell(3,0).isTowerCompleted()==true);

        }
        //wrong worker
        @Test
        void WrongBuildAfterMove2() {
            PlayerMessage message=new PlayerBuildChoice(new View(),player,1,3,0,"Block");
            controller.update(message);
            //wrong worker

            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );

        }
        //not surrounding cell
        @Test
        void WrongBuildAfterMove3() {

            PlayerMessage message=new PlayerBuildChoice(new View(),player,0,2,2,"Block");
            controller.update(message);
            //wrong worker

            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );
        }

        //building a block to a space reserved to a dome
        @Test
        void WrongBuildAfterMove4() {
            player.getWorker(0).movedToPosition(2, 2, 0);
            PlayerMessage message=new PlayerBuildChoice(new View(),player,0,3,2,"Block");
            controller.update(message);


            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, false, 0, 0, false, false );
        }

        @Test //ok
        void BuildAfterMove2() {

            PlayerMessage message=new PlayerBuildChoice(new View(),player,0,3,0,"Block");
            controller.update(message);
            //should work

            //turnInfo must have been updated
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 1, 0, true, true );

            //checking that tower's height increased
            assertTrue(gameBoard.getTowerCell(3,0).getTowerHeight()==3);
            //checking that the piece is right
            assertTrue(gameBoard.getTowerCell(3,0).getLevel(2).getPiece() instanceof Level3Block);
            //checking that tower is not completed
            assertTrue(gameBoard.getTowerCell(3,0).isTowerCompleted()==false);



        }

    }

    @Nested
            //build done, should only end
    class AfterSecondBuild{

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

        @Test
        void MoveAfterBuild() {
            PlayerMessage message = new PlayerMovementChoice(new View(), player, 1, 0, 3);
            controller.update(message);
            //can't execute because has already built

            //turnInfo Must be the initial one
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 1, 1, true, true);

        }

        @Test
        void BuildAfterBuild() {
            PlayerMessage message = new PlayerMovementChoice(new View(), player, 1, 0, 3);
            controller.update(message);
            //can't execute because has already built

            //turnInfo Must be the initial one
            testSupportFunctions.baseTurnInfoChecker(turnInfo, true, 1, true, 1, 1, true, true);

        }

        @Test
        void EndAfterBuild() {
            PlayerMessage message=new PlayerEndOfTurnChoice(new View(),player);
            controller.update(message);
            //correct end of turn

            //turnInfo must have been reset
            testSupportFunctions.baseTurnInfoChecker(turnInfo,false,0,false,0,-1,false,false);

        }

    }




}
*/