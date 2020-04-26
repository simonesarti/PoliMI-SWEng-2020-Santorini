package it.polimi.ingsw.model.strategy.buildstrategy;
import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.piece.Dome;
import it.polimi.ingsw.model.piece.Level2Block;
import it.polimi.ingsw.model.piece.Level3Block;
import it.polimi.ingsw.model.strategy.movestrategy.BasicMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class HephaestusBuildTest {

    BasicMove basicmove;

    HephaestusBuild hephaestusbuild;
    GameBoard gameBoard;
    TurnInfo turnInfo;
    Player player;
    String piece;
    Player enemy1Player;
    Player enemy2Player;
    PlayerInfo playerInfo;
    PlayerInfo enemy1Info;
    PlayerInfo enemy2Info;
    int[] buildingTo = new int[2];


    @BeforeEach
    void init(){
        basicmove = new BasicMove();

        hephaestusbuild = new HephaestusBuild();
        playerInfo  =new PlayerInfo("Gianpaolo",new GregorianCalendar(1970, Calendar.JULY, 15),3);
        player = new Player(playerInfo);
        player.setColour(Colour.WHITE);
        player.getWorker(0).setStartingPosition(0,0);



        enemy1Info  =new PlayerInfo("enemy1",new GregorianCalendar(2000, Calendar.NOVEMBER, 30),3);
        enemy1Player = new Player(playerInfo);
        enemy1Player.setColour(Colour.BLUE);
        enemy1Player.getWorker(0).setStartingPosition(0,1);
        enemy1Player.getWorker(1).setStartingPosition(0,2);

        enemy2Info  =new PlayerInfo("enemy2",new GregorianCalendar(1999, Calendar.DECEMBER, 7),3);
        enemy2Player = new Player(playerInfo);
        enemy2Player.setColour(Colour.GREY);
        enemy2Player.getWorker(0).setStartingPosition(0,3);
        enemy2Player.getWorker(1).setStartingPosition(0,4);

        gameBoard = new GameBoard();

        turnInfo = new TurnInfo();

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
    void checkBuild (){


        //player must have moved
        turnInfo.setChosenWorker(0);
        buildingTo[0]=1;
        buildingTo[1]=1;

        assertEquals(GameMessage.hasNotMoved, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        turnInfo.turnInfoReset();


        //if hephaestus has already built one time, the next building destination must be the "previous position"
        //and the piece must not be a dome
        turnInfo.setChosenWorker(0);
        turnInfo.setHasMoved();
        turnInfo.addBuild();
        turnInfo.setHasBuilt();
        turnInfo.setLastBuildCoordinates(2,1);

        buildingTo[0]=1;
        buildingTo[1]=1;
        piece = "Block";

        assertEquals(GameMessage.HephaestusWrongBuild, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        turnInfo.setLastBuildCoordinates(1,1);
        piece = "Dome";

        assertEquals(GameMessage.mustBeBlock, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        turnInfo.turnInfoReset();

        //x and y must be inside the board
        turnInfo.setChosenWorker(0);
        turnInfo.setHasMoved();
        buildingTo[0]=28;
        buildingTo[1]=1;
        assertEquals(GameMessage.notInGameBoard, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //worker must be the same that has moved
        buildingTo[0]=1;
        buildingTo[1]=1;

        turnInfo.setChosenWorker(1);
        assertEquals(GameMessage.NotSameWorker, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        turnInfo.setChosenWorker(0);

        //workerPosition must not be the building position
        buildingTo[0]=player.getWorker(0).getCurrentPosition().getX();
        buildingTo[1]=player.getWorker(0).getCurrentPosition().getY();
        assertEquals(GameMessage.notOwnPosition, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //workerPosition must be adjacent to building position
        buildingTo[0]=2;
        buildingTo[1]=0;
        assertEquals(GameMessage.notInSurroundings, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //towerCell must not be completed by a dome
        player.getWorker(0).movedToPosition(0,3,0);
        buildingTo[0]=1;
        buildingTo[1]=3;

        assertEquals(GameMessage.noBuildToCompleteTower, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //the chosen piece must not be a "Block" when tower's height is 3
        piece = "Block";
        player.getWorker(0).movedToPosition(2,2,0);
        buildingTo[0]=3;
        buildingTo[1]=2;
        assertEquals(GameMessage.noBlocksInDome, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //the chosen piece must not be a "Dome" when tower's height is <3
        piece = "Dome";
        buildingTo[0]=2;
        buildingTo[1]=3;
        assertEquals(GameMessage.noDomesInBlock, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //there must not be a worker in the building position
        player.getWorker(0).movedToPosition(1,4,0);
        piece = "Block";
        buildingTo[0]=2;
        buildingTo[1]=4;
        assertEquals(GameMessage.noBuildToOccupiedTower, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));


        player.getWorker(0).movedToPosition(1,4,0);
        piece = "Block";
        buildingTo[0]=2;
        buildingTo[1]=3;
        //build ok
        assertEquals(GameMessage.buildOK, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

    }


    @Test
    void buildTesting(){

        //EVERYTHING IS RIGHT
        //Block test
        turnInfo.setChosenWorker(0);
        piece = "Block";
        player.getWorker(0).movedToPosition(2,2,0);
        turnInfo.setHasMoved();
        buildingTo[0]=2;
        buildingTo[1]=3;
        assertEquals(GameMessage.buildOK, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        assertEquals(GameMessage.buildAgainOrEnd, hephaestusbuild.build(turnInfo, gameBoard,player,0,buildingTo, piece));
        //control that tower's height increased
        assertTrue(gameBoard.getTowerCell(2,3).getTowerHeight()==2);
        //control that the piece is right
        assertTrue(gameBoard.getTowerCell(2,3).getLevel(1).getPiece() instanceof Level2Block);
        //control that tower is not completed
        assertTrue(gameBoard.getTowerCell(2,3).isTowerCompleted()==false);
        assertEquals(1,turnInfo.getNumberOfBuilds());
        assertTrue(turnInfo.getHasAlreadyBuilt());
        assertTrue(turnInfo.getTurnCanEnd()==true);



        //second build test

        turnInfo.setChosenWorker(0);
        piece = "Block";
        player.getWorker(0).movedToPosition(2,2,0);
        turnInfo.setHasMoved();
        buildingTo[0]=2;
        buildingTo[1]=3;
        assertEquals(GameMessage.buildOK, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        assertEquals(GameMessage.turnCompleted, hephaestusbuild.build(turnInfo, gameBoard,player,0,buildingTo, piece));
        //control that tower's height increased
        assertTrue(gameBoard.getTowerCell(2,3).getTowerHeight()==3);
        //control that the piece is right
        assertTrue(gameBoard.getTowerCell(2,3).getLevel(2).getPiece() instanceof Level3Block);
        //control that tower is not completed
        assertTrue(gameBoard.getTowerCell(3,2).isTowerCompleted()==false);
        assertEquals(2,turnInfo.getNumberOfBuilds());
        assertTrue(turnInfo.getTurnHasEnded()==true);

        //TRY TO BUILD USING A DOME
        //Dome test

        turnInfo.turnInfoReset();
        turnInfo.setChosenWorker(0);
        piece = "Dome";
        player.getWorker(0).movedToPosition(2,2,0);
        turnInfo.setHasMoved();
        buildingTo[0]=3;
        buildingTo[1]=2;
        assertEquals(GameMessage.buildOK, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        assertEquals(GameMessage.buildAgainOrEnd, hephaestusbuild.build(turnInfo, gameBoard,player,0,buildingTo, piece));
        //control that tower's height increased
        assertTrue(gameBoard.getTowerCell(3,2).getTowerHeight()==4);
        //control that the piece is right
        assertTrue(gameBoard.getTowerCell(3,2).getLevel(3).getPiece() instanceof Dome);
        //control that tower is completed
        assertTrue(gameBoard.getTowerCell(3,2).isTowerCompleted()==true);
        assertTrue(turnInfo.getHasAlreadyBuilt());
        assertTrue(turnInfo.getTurnCanEnd()==true);
        assertEquals(1,turnInfo.getNumberOfBuilds());

        //second build test
        turnInfo.setChosenWorker(0);
        piece = "Dome";
        player.getWorker(0).movedToPosition(2,2,0);
        turnInfo.setHasMoved();
        buildingTo[0]=3;
        buildingTo[1]=2;
        assertEquals(GameMessage.mustBeBlock, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));

        //control that nothing changed
        assertTrue(gameBoard.getTowerCell(3,2).getTowerHeight()==4);

        assertTrue(gameBoard.getTowerCell(3,2).getLevel(3).getPiece() instanceof Dome);

        assertTrue(gameBoard.getTowerCell(3,2).isTowerCompleted()==true);
        assertTrue(turnInfo.getTurnCanEnd()==true);
        assertEquals(1,turnInfo.getNumberOfBuilds());


        //TRY TO BUILD A BLOCK ON A LEVEL 4
        turnInfo.turnInfoReset();
        turnInfo.setChosenWorker(0);
        piece = "Block";
        player.getWorker(0).movedToPosition(2,3,1);
        turnInfo.setHasMoved();
        buildingTo[0]=3;
        buildingTo[1]=3;
        assertEquals(GameMessage.buildOK, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        assertEquals(GameMessage.buildAgainOrEnd, hephaestusbuild.build(turnInfo, gameBoard,player,0,buildingTo, piece));
        //control that tower's height increased
        assertTrue(gameBoard.getTowerCell(3,3).getTowerHeight()==3);
        //control that the piece is right
        assertTrue(gameBoard.getTowerCell(3,3).getLevel(2).getPiece() instanceof Level3Block);
        //control that tower is not completed
        assertTrue(gameBoard.getTowerCell(2,3).isTowerCompleted()==false);
        assertEquals(1,turnInfo.getNumberOfBuilds());
        assertTrue(turnInfo.getHasAlreadyBuilt());
        assertTrue(turnInfo.getTurnCanEnd()==true);

        //second build to a complete tower
        turnInfo.setChosenWorker(0);
        piece = "Block";
        player.getWorker(0).movedToPosition(2,3,1);
        turnInfo.setHasMoved();
        buildingTo[0]=3;
        buildingTo[1]=3;
        assertEquals(GameMessage.noBlocksInDome, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        //control that nothing changed

        assertTrue(gameBoard.getTowerCell(3,3).getTowerHeight()==3);

        assertTrue(gameBoard.getTowerCell(3,3).getLevel(2).getPiece() instanceof Level3Block);

        assertTrue(gameBoard.getTowerCell(2,3).isTowerCompleted()==false);
        assertEquals(1,turnInfo.getNumberOfBuilds());
        assertTrue(turnInfo.getHasAlreadyBuilt());
        assertTrue(turnInfo.getTurnCanEnd()==true);

        //TRY TO BUILD IN A DIFFERENT POSITION

        turnInfo.turnInfoReset();
        turnInfo.setChosenWorker(0);
        piece = "Block";
        player.getWorker(0).movedToPosition(2,1,0);
        turnInfo.setHasMoved();
        buildingTo[0]=2;
        buildingTo[1]=0;
        assertEquals(GameMessage.buildOK, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        assertEquals(GameMessage.buildAgainOrEnd, hephaestusbuild.build(turnInfo, gameBoard,player,0,buildingTo, piece));
        //control that tower's height increased
        assertTrue(gameBoard.getTowerCell(2,3).getTowerHeight()==3);
        //control that the piece is right
        assertTrue(gameBoard.getTowerCell(2,0).getLevel(2).getPiece() instanceof Level3Block);
        //control that tower is not completed
        assertTrue(gameBoard.getTowerCell(2,0).isTowerCompleted()==false);
        assertEquals(1,turnInfo.getNumberOfBuilds());
        assertTrue(turnInfo.getHasAlreadyBuilt());
        assertTrue(turnInfo.getTurnCanEnd()==true);

        //second build to a different position

        turnInfo.setChosenWorker(0);
        piece = "Block";
        turnInfo.setHasMoved();
        buildingTo[0]=3;
        buildingTo[1]=0;
        assertEquals(GameMessage.HephaestusWrongBuild, hephaestusbuild.checkBuild(turnInfo, gameBoard,player,0,buildingTo, piece));
        //control that nothing changed
        assertTrue(gameBoard.getTowerCell(2,3).getTowerHeight()==3);

        assertTrue(gameBoard.getTowerCell(2,0).getLevel(2).getPiece() instanceof Level3Block);

        assertTrue(gameBoard.getTowerCell(2,0).isTowerCompleted()==false);
        assertEquals(1,turnInfo.getNumberOfBuilds());
        assertTrue(turnInfo.getHasAlreadyBuilt());
        assertTrue(turnInfo.getTurnCanEnd()==true);


















    }


}