package it.polimi.ingsw.supportClasses;

import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.piece.Dome;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.ServerSideConnection;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class TestSupportFunctions {

        public void baseTurnInfoChecker(TurnInfo turnInfo, boolean hasMoved, int numberOfMoves, boolean hasBuilt, int numberOfBuilds, int chosenWorker, boolean turnCanEnd, boolean turnHasEnded){
            assertEquals(turnInfo.getHasAlreadyMoved(),hasMoved);
            assertEquals(turnInfo.getNumberOfMoves(),numberOfMoves);
            assertEquals(turnInfo.getHasAlreadyBuilt(),hasBuilt);
            assertEquals(turnInfo.getNumberOfBuilds(),numberOfBuilds);
            assertEquals(turnInfo.getChosenWorker(),chosenWorker);
            assertEquals(turnInfo.getTurnCanEnd(),turnCanEnd);
            assertEquals(turnInfo.getTurnHasEnded(),turnHasEnded);

        }

    public void drawnGameBoardInit(PlayerInfo playerInfo, PlayerInfo enemy1Info, PlayerInfo enemy2Info, Player player, Player enemy1Player, Player enemy2Player, GameBoard gameBoard, TurnInfo turnInfo){



        player.setColour(Colour.WHITE);
        player.getWorker(0).setStartingPosition(0,0);
        player.getWorker(1).setStartingPosition(0,0);


        enemy1Player.setColour(Colour.BLUE);
        enemy1Player.getWorker(0).setStartingPosition(0,1);
        enemy1Player.getWorker(1).setStartingPosition(0,2);


        enemy2Player.setColour(Colour.GREY);
        enemy2Player.getWorker(0).setStartingPosition(0,3);
        enemy2Player.getWorker(1).setStartingPosition(0,4);


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


        return;
    }






}

