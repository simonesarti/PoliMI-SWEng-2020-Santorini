package it.polimi.ingsw.model.strategy.IntegrationTest;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.BuildData;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.MoveData;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerBuildChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerEndOfTurnChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMovementChoice;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.supportClasses.EmptyVirtualView;
import it.polimi.ingsw.supportClasses.TestSupportFunctions;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;


public class PanIntegrationTest {

    GodCard pan;
    Model model;
    EmptyVirtualView vv;
    Controller controller;
    TurnInfo turnInfo;
    GameBoard gameBoard;

    Player testPlayer;
    Player enemy1Player;
    Player enemy2Player;
    PlayerInfo playerInfo;
    PlayerInfo enemy1Info;
    PlayerInfo enemy2Info;

    TestSupportFunctions testSupportFunctions=new TestSupportFunctions();

    @BeforeEach
    void init() {

        model = new Model(3);
        vv = new EmptyVirtualView();
        controller = new Controller(model);

        gameBoard = model.getGameBoard();
        turnInfo = model.getTurnInfo();

        playerInfo = new PlayerInfo("xXoliTheQueenXx", new GregorianCalendar(1998, Calendar.SEPTEMBER, 9),3);
        testPlayer = new Player(playerInfo);
        testPlayer.setColour(Colour.WHITE);
        testPlayer.getWorker(0).setStartingPosition(0, 0);
        testPlayer.getWorker(1).setStartingPosition(1, 0);

        enemy1Info = new PlayerInfo("enemy1", new GregorianCalendar(2000, Calendar.NOVEMBER, 30),3);
        enemy1Player = new Player(playerInfo);
        enemy1Player.setColour(Colour.BLUE);
        enemy1Player.getWorker(0).setStartingPosition(0, 1);
        enemy1Player.getWorker(1).setStartingPosition(0, 2);

        enemy2Info = new PlayerInfo("enemy2", new GregorianCalendar(1999, Calendar.DECEMBER, 7),3);
        enemy2Player = new Player(playerInfo);
        enemy2Player.setColour(Colour.GREY);
        enemy2Player.getWorker(0).setStartingPosition(0, 3);
        enemy2Player.getWorker(1).setStartingPosition(0, 4);

        //Instancing testPlayer's godcard
        String godDataString[] = {"Pan", "God of the Wild", "Simple", "true", "You also win if your Worker moves down two or more levels."};
        pan = new GodCard(godDataString);
        testPlayer.setGodCard(pan);
    }

    @Nested
    class FirstChoice{

        @BeforeEach
        void init(){

            //GAMEBOARD GENERATION
            int[][] towers=
                    {
                            {2,4,1,2,2},
                            {3,1,2,1,4},
                            {4,1,0,3,4},
                            {0,2,1,4,4},
                            {3,2,1,4,0}
                    };

            gameBoard.generateBoard(towers);


            //POSITIONING TEST WORKERS
            gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().setWorker(testPlayer.getWorker(0));
            testPlayer.getWorker(0).movedToPosition(1,1,1);

            gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().setWorker(testPlayer.getWorker(1));
            testPlayer.getWorker(1).movedToPosition(2,3,1);

            //POSITIONING OPPONENT WORKERS

            gameBoard.getTowerCell(0,1).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(0));
            enemy1Player.getWorker(0).movedToPosition(0,1,3);

            gameBoard.getTowerCell(2,2).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(1));
            enemy1Player.getWorker(1).movedToPosition(2,2,0);

            gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(0));
            enemy2Player.getWorker(0).movedToPosition(4,4,0);

            gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(1));
            enemy2Player.getWorker(1).movedToPosition(2,4,1);
        }

        //turn starts here, only move should be performed

        @Test
        void EndBeforeEverything(){

            PlayerMessage message=new PlayerEndOfTurnChoice(vv,testPlayer);
            controller.update(message);
            //method returns immediately

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo,false,0,false,0,-1,false,false);
        }

        @Test
        void BuildBeforeEverything(){

            PlayerMessage message=new PlayerBuildChoice(vv,testPlayer,new BuildData(1,1,2,"Block"));
            controller.update(message);

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo,false,0,false,0,-1,false,false);
        }

        @Test
        void WrongMoveBeforeEverything(){
            PlayerMessage message=new PlayerMovementChoice(vv,testPlayer,new MoveData(0,1,0));
            controller.update(message);
            //invalid move, denied

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo,false,0,false,0,-1,false,false);
        }

        @Test
        void WrongMoveBeforeEverything2(){
            PlayerMessage message=new PlayerMovementChoice(vv,testPlayer,new MoveData(-1,1,0));
            controller.update(message);
            //invalid move, denied, invalid worker

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo,false,0,false,0,-1,false,false);
        }

        @Test //ok
        void CorrectMoveBeforeEverything(){
            PlayerMessage message=new PlayerMovementChoice(vv,testPlayer,new MoveData(1,1,2));
            controller.update(message);

            //turnInfo must have been modified
            testSupportFunctions.baseTurnInfoChecker(turnInfo,true,1,false,0,1,false,false);
        }

        @Test //ok
        void CorrectMoveAndWinBasic(){
            //repositioning worker
            gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().workerMoved();
            gameBoard.getTowerCell(1,4).getFirstNotPieceLevel().setWorker(testPlayer.getWorker(1));
            testPlayer.getWorker(1).movedToPosition(1,4,2);

            PlayerMessage message=new PlayerMovementChoice(vv,testPlayer,new MoveData(1,0,4));
            controller.update(message);
            //should return victory message

            //turnInfo must have been modified
            testSupportFunctions.baseTurnInfoChecker(turnInfo,true,1,false,0,1,false,false);
        }

        @Test //ok
        void CorrectMoveAndSpecificWin(){
            //repositioning worker
            gameBoard.getTowerCell(2,3).getFirstNotPieceLevel().workerMoved();
            gameBoard.getTowerCell(0,4).getFirstNotPieceLevel().setWorker(testPlayer.getWorker(1));
            testPlayer.getWorker(1).movedToPosition(0,4,3);

            PlayerMessage message=new PlayerMovementChoice(vv,testPlayer,new MoveData(1,0,3));
            controller.update(message);
            //should return victory message

            //turnInfo must have been modified
            testSupportFunctions.baseTurnInfoChecker(turnInfo,true,1,false,0,1,false,false);
        }

    }

    @Nested
    class SecondChoice {

        @BeforeEach
        void init() {

            //SETS INITIAL STATE
            turnInfo.setChosenWorker(1);
            turnInfo.setHasMoved();
            turnInfo.addMove();

            //GAMEBOARD GENERATION
            int[][] towers=
                    {
                            {2,4,1,2,2},
                            {3,1,2,1,4},
                            {4,1,0,3,4},
                            {0,2,1,4,4},
                            {0,1,1,4,0}
                    };

            gameBoard.generateBoard(towers);


            //POSITIONING TEST WORKERS
            gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().setWorker(testPlayer.getWorker(0));
            testPlayer.getWorker(0).movedToPosition(1,1,1);

            gameBoard.getTowerCell(1,2).getFirstNotPieceLevel().setWorker(testPlayer.getWorker(1));
            testPlayer.getWorker(1).movedToPosition(1,2,1);

            //POSITIONING OPPONENT WORKERS

            gameBoard.getTowerCell(0,1).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(0));
            enemy1Player.getWorker(0).movedToPosition(0,1,3);

            gameBoard.getTowerCell(2,2).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(1));
            enemy1Player.getWorker(1).movedToPosition(2,2,0);

            gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(0));
            enemy2Player.getWorker(0).movedToPosition(4,4,0);

            gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(1));
            enemy2Player.getWorker(1).movedToPosition(2,4,1);

        }

        //move done, only build should be performed

        @Test
        void EndAftertMove() {

            PlayerMessage message=new PlayerEndOfTurnChoice(vv,testPlayer);
            controller.update(message);
            //method returns immediately

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo,true,1,false,0,1,false,false);
        }

        @Test
        void MoveAfterMove() {

            PlayerMessage message=new PlayerMovementChoice(vv,testPlayer,new MoveData(1,2,3));
            controller.update(message);
            //method returns because the player has already moved

            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo,true,1,false,0,1,false,false);
        }

        @Test
        void WrongBuildAfterMove() {
            PlayerMessage message=new PlayerBuildChoice(vv,testPlayer,new BuildData(0,1,2,"Dome"));
            controller.update(message);
            //every parameter is wrong, should give error for the wrong worker

            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo,true,1,false,0,1,false,false);
        }

        @Test
        void WrongBuildAfterMove2() {
            PlayerMessage message=new PlayerBuildChoice(vv,testPlayer,new BuildData(1,0,3,"Dome"));
            controller.update(message);
            //can't place dome here error

            //turnInfo must not have been modified since this class's BeforeEach
            testSupportFunctions.baseTurnInfoChecker(turnInfo,true,1,false,0,1,false,false);
        }

        @Test //ok
        void BuildAfterMove() {
            PlayerMessage message=new PlayerBuildChoice(vv,testPlayer,new BuildData(1,0,3,"Block"));
            controller.update(message);
            //should work

            //turnInfo must have been updated
            testSupportFunctions.baseTurnInfoChecker(turnInfo,true,1,true,1,1,true,true);
        }
    }

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

            //GAMEBOARD GENERATION
            int[][] towers=
                    {
                            {2,4,1,2,2},
                            {3,1,2,1,4},
                            {4,1,0,3,4},
                            {1,2,1,4,4},
                            {0,1,1,4,0}
                    };

            gameBoard.generateBoard(towers);

            //POSITIONING TEST WORKERS
            gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().setWorker(testPlayer.getWorker(0));
            testPlayer.getWorker(0).movedToPosition(1,1,1);

            gameBoard.getTowerCell(1,2).getFirstNotPieceLevel().setWorker(testPlayer.getWorker(1));
            testPlayer.getWorker(1).movedToPosition(1,2,1);

            //POSITIONING OPPONENT WORKERS

            gameBoard.getTowerCell(0,1).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(0));
            enemy1Player.getWorker(0).movedToPosition(0,1,3);

            gameBoard.getTowerCell(2,2).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(1));
            enemy1Player.getWorker(1).movedToPosition(2,2,0);

            gameBoard.getTowerCell(4,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(0));
            enemy2Player.getWorker(0).movedToPosition(4,4,0);

            gameBoard.getTowerCell(2,4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(1));
            enemy2Player.getWorker(1).movedToPosition(2,4,1);

        }

        //turn finishes here, only end should be performed

        @Test
        void MoveAfterFinish() {
            PlayerMessage message=new PlayerMovementChoice(vv,testPlayer,new MoveData(1,2,3));
            controller.update(message);
            //can't execute because turn has ended

            //turnInfo Must be the initial one
            testSupportFunctions.baseTurnInfoChecker(turnInfo,true,1,true,1,1,true,true);
        }

        @Test
        void BuildAfterFinish() {
            PlayerMessage message=new PlayerBuildChoice(vv,testPlayer,new BuildData(1,0,3,"Block"));
            controller.update(message);
            //can't execute because turn has ended

            //turnInfo Must be the initial one
            testSupportFunctions.baseTurnInfoChecker(turnInfo,true,1,true,1,1,true,true);
        }

        @Test
        void EndAfterFinish() {
            PlayerMessage message=new PlayerEndOfTurnChoice(vv,testPlayer);
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
            testSupportFunctions.baseTurnInfoChecker(turnInfo,false,0,false,0,-1,false,false);

            assertEquals(Colour.BLUE,model.getTurn());
        }
    }










}
