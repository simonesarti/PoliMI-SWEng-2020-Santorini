package it.polimi.ingsw.model.strategy.buildstrategy;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.piece.*;
import it.polimi.ingsw.model.strategy.CheckSupportFunctions;

public class PrometheusBuild implements BuildStrategy{

    @Override
    public String checkBuild(TurnInfo turnInfo, GameBoard gameboard, Player player, int chosenWorker, int[] buildingInto, String pieceType) {

        CheckSupportFunctions support=new CheckSupportFunctions();
        int x = buildingInto[0];
        int y = buildingInto[1];

        //he has already built twice he should not be able to call any command other than END
        if(turnInfo.getNumberOfBuilds()>=2){
            return GameMessage.noBuildMoreThanTwice;
        }

        //If the player has already built once but not moved, he must move before being allowed to build again
        if(!turnInfo.getHasAlreadyMoved() && turnInfo.getNumberOfBuilds()==1){
            return GameMessage.hasNotMoved;
        }

        //chosenWorker must be a valid number
        if(support.invalidWorkerNumber(chosenWorker)){
            return GameMessage.invalidWorkerNumber;
        }
        Worker worker = player.getWorker(chosenWorker);

        //x e y must be inside the board
        if (support.notInGameBoard(x,y)) {
            return GameMessage.notInGameBoard;
        }
        int z = gameboard.getTowerCell(x, y).getTowerHeight();

        //if worker has already moved, the player must use the same worker(player can't get here with a second build without move)
        //therefore this check only concerns the second build
        if(turnInfo.getHasAlreadyMoved()){
            if(chosenWorker != turnInfo.getChosenWorker()) {
                return GameMessage.NotSameWorker;
            }
        }

        //workerPosition must not be the destination position
        if (support.notOwnPosition(worker,x,y)){
            return GameMessage.notOwnPosition;
        }

        //workerPosition must be adjacent to buildingPosition
        if (support.notInSurroundings(worker,x,y)){
            return GameMessage.notInSurroundings;
        }

        //tower must not be completed
        if(support.completeTower(gameboard,x,y)) {
            return GameMessage.noBuildToCompleteTower;
        }

        //there must not be a worker in the building position
        if (support.occupiedTower(gameboard,x,y)){
            return GameMessage.noBuildToOccupiedTower;
        }

        //the chosen piece must not be a "Block" when tower's height is 3
        if(z==3 &&  pieceType.equals("Block")){
            return GameMessage.noBlocksInDome;
        }

        //the chosen piece must not be a "Dome" when tower's height is <3
        if(z<3 &&  pieceType.equals("Dome")) {
            return GameMessage.noDomesInBlock;
        }

        return GameMessage.buildOK;

    }



    @Override
    public String build(TurnInfo turnInfo, GameBoard gameboard, Player player, int chosenWorker, int[] buildingInto, String pieceType) {

        int x = buildingInto[0];
        int y = buildingInto[1];
        int z = gameboard.getTowerCell(x, y).getTowerHeight();
        Piece piece = null;

        //create the right Piece

        if (z==0){
            piece = new Level1Block();
        }
        else if (z==1){
            piece = new Level2Block();
        }
        else if (z==2){
            piece = new Level3Block();
        }
        else if (z==3){
            piece = new Dome();
        }

        //set the Piece
        gameboard.getTowerCell(x,y).getFirstNotPieceLevel().setPiece(piece);
        //increase tower's height
        gameboard.getTowerCell(x, y).increaseTowerHeight();
        //check if tower is complete
        gameboard.getTowerCell(x,y).checkCompletion();

        turnInfo.setHasBuilt();
        turnInfo.addBuild();

        if(!turnInfo.getHasAlreadyMoved()) {
            turnInfo.setChosenWorker(chosenWorker);
            return GameMessage.moveRequest;
        }else{
            turnInfo.setTurnCanEnd();
            turnInfo.setTurnHasEnded();
            return GameMessage.turnCompleted;
        }


    }

}
