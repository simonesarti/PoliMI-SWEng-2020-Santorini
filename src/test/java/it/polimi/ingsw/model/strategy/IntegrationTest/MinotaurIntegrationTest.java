package it.polimi.ingsw.model.strategy.IntegrationTest;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerBuildChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerEndOfTurnChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMovementChoice;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class MinotaurIntegrationTest {

    GodCard minotaur;
    Model model;
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
        controller = new Controller(model);

        gameBoard = model.getGameBoard();
        turnInfo = model.getTurnInfo();

        playerInfo = new PlayerInfo("xXoliTheQueenXx", new GregorianCalendar(1998, Calendar.SEPTEMBER, 9));
        testPlayer = new Player(playerInfo);
        testPlayer.setColour(Colour.WHITE);
        testPlayer.getWorker(0).setStartingPosition(0, 0);
        testPlayer.getWorker(1).setStartingPosition(1, 0);

        enemy1Info = new PlayerInfo("enemy1", new GregorianCalendar(2000, Calendar.NOVEMBER, 30));
        enemy1Player = new Player(playerInfo);
        enemy1Player.setColour(Colour.BLUE);
        enemy1Player.getWorker(0).setStartingPosition(0, 1);
        enemy1Player.getWorker(1).setStartingPosition(0, 2);

        enemy2Info = new PlayerInfo("enemy2", new GregorianCalendar(1999, Calendar.DECEMBER, 7));
        enemy2Player = new Player(playerInfo);
        enemy2Player.setColour(Colour.GREY);
        enemy2Player.getWorker(0).setStartingPosition(0, 3);
        enemy2Player.getWorker(1).setStartingPosition(0, 4);

        //Instancing testPlayer's godcard
        String godDataString[] = {"Minotaur", "Bull-headed Monster", "Simple", "true", "Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level."};
        minotaur = new GodCard(godDataString);
        testPlayer.setGodCard(minotaur);
    }

    //should only be able to move
    @Nested
    class FirstChoice{

        @BeforeEach
        void init() {

            //GAMEBOARD GENERATION
            int[][] towers =
                    {
                            {2, 4, 1, 2, 2},
                            {3, 1, 2, 1, 4},
                            {4, 1, 0, 3, 4},
                            {0, 2, 2, 4, 4},
                            {3, 2, 1, 4, 0}
                    };

            gameBoard.generateBoard(towers);


            //POSITIONING TEST WORKERS
            gameBoard.getTowerCell(1, 1).getFirstNotPieceLevel().setWorker(testPlayer.getWorker(0));
            testPlayer.getWorker(0).movedToPosition(1, 1, 1);

            gameBoard.getTowerCell(2, 3).getFirstNotPieceLevel().setWorker(testPlayer.getWorker(1));
            testPlayer.getWorker(1).movedToPosition(2, 3, 2);

            //POSITIONING OPPONENT WORKERS

            gameBoard.getTowerCell(0, 1).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(0));
            enemy1Player.getWorker(0).movedToPosition(0, 1, 3);

            gameBoard.getTowerCell(2, 2).getFirstNotPieceLevel().setWorker(enemy1Player.getWorker(1));
            enemy1Player.getWorker(1).movedToPosition(2, 2, 0);

            gameBoard.getTowerCell(2, 1).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(0));
            enemy2Player.getWorker(0).movedToPosition(2, 1, 2);

            gameBoard.getTowerCell(2, 4).getFirstNotPieceLevel().setWorker(enemy2Player.getWorker(1));
            enemy2Player.getWorker(1).movedToPosition(2, 4, 1);
        }

        @Test
        void EndBeforeEverything(){
            PlayerMessage message = new PlayerEndOfTurnChoice(new View(), testPlayer);
            controller.update(message);
            //method returns immediately, can't end yet

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo,false,0,false,0,-1,false,false);
        }

        @Test
        void buildBeforeEverything(){
            PlayerMessage message = new PlayerBuildChoice(new View(), testPlayer, -2, 0, 0, "Block");
            controller.update(message);
            //wrong worker, but should give error for missing move

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo,false,0,false,0,-1,false,false);
        }

        @Test
        void NoPushableMoveBeforeEverything(){
            PlayerMessage message = new PlayerMovementChoice(new View(), testPlayer, 0, 2, 2);
            controller.update(message);
            //opponent can't be pushed, invalid move

            //turnInfo must still have all his initial values
            testSupportFunctions.baseTurnInfoChecker(turnInfo,false,0,false,0,-1,false,false);

            assertEquals(testPlayer.getWorker(0),gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().getWorker());
            assertEquals(enemy1Player.getWorker(1),gameBoard.getTowerCell(2,2).getFirstNotPieceLevel().getWorker());
        }

        @Test
        void PushableMoveBeforeEverythingAndWin(){

            PlayerMessage message = new PlayerMovementChoice(new View(), testPlayer, 0, 2, 1);
            controller.update(message);
            //opponent can pushed, valid move

            testSupportFunctions.baseTurnInfoChecker(turnInfo,true,1,false,0,0,false,false);


            //enemy worker pushed
            assertEquals(enemy2Player.getWorker(0),gameBoard.getTowerCell(3,1).getFirstNotPieceLevel().getWorker());
            assertAll(
                    ()->assertEquals(3,enemy2Player.getWorker(0).getCurrentPosition().getX()),
                    ()->assertEquals(1,enemy2Player.getWorker(0).getCurrentPosition().getY()),
                    ()->assertEquals(1,enemy2Player.getWorker(0).getCurrentPosition().getZ()),
                    ()->assertEquals(2,enemy2Player.getWorker(0).getPreviousPosition().getX()),
                    ()->assertEquals(1,enemy2Player.getWorker(0).getPreviousPosition().getY()),
                    ()->assertEquals(2,enemy2Player.getWorker(0).getPreviousPosition().getZ())

            );

            //e
            assertEquals(testPlayer.getWorker(0),gameBoard.getTowerCell(2,1).getFirstNotPieceLevel().getWorker());
            assertAll(
                    ()->assertEquals(2,testPlayer.getWorker(0).getCurrentPosition().getX()),
                    ()->assertEquals(1,testPlayer.getWorker(0).getCurrentPosition().getY()),
                    ()->assertEquals(2,testPlayer.getWorker(0).getCurrentPosition().getZ()),
                    ()->assertEquals(1,testPlayer.getWorker(0).getPreviousPosition().getX()),
                    ()->assertEquals(1,testPlayer.getWorker(0).getPreviousPosition().getY()),
                    ()->assertEquals(1,testPlayer.getWorker(0).getPreviousPosition().getZ())

            );
            assertNull(gameBoard.getTowerCell(1,1).getFirstNotPieceLevel().getWorker());

        }
    }

    //should only be able to build (after move)
    @Nested
    class SecondChoice{

        @Test
        void EndAfterMove(){}

        @Test
        void moveAfterMove(){}

        @Test
        void wrongBuildAfterMove(){}

        @Test
        void correctBuildAfterEverything(){}
    }

    //should only be able to end (after move and build)
    @Nested
    class ThirdChoice{

        @Test
        void EndAfterFinish(){}

        @Test
        void MoveAfterFinish(){}

        @Test
        void BuildAfterFinish(){}
    }
}
