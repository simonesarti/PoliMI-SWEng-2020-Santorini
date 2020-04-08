package it.polimi.ingsw.model.strategy.buildstrategy;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.PlayerMovementChoice;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.piece.*;
import it.polimi.ingsw.messages.PlayerBuildChoice;


public class BasicBuild implements BuildStrategy {

    boolean alreadyMoved;

    public BasicBuild(){
        this.alreadyMoved = false;
    }

    /**
     * Checks if player can build
     *
     * @param gameboard
     * @param message message PlayerBuildChoice
     * @return
     */
   @Override
   public String checkBuild(GameBoard gameboard, PlayerBuildChoice message){

       Worker worker = message.getPlayer().getWorker(message.getChosenWorker());
       int x = message.getBuildingInto()[0];
       int y = message.getBuildingInto()[1];
       int z = gameboard.getTowerCell(x, y).getTowerHeight();

       Position workerStartingPosition = new Position(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY(),worker.getCurrentPosition().getZ());

       //alreadyMoved must be false
       if(alreadyMoved){
           return GameMessage.alreadyMoved;
       }
       //x e y must be inside the board
       if (x < 0 || x > 4 || y < 0 || y > 4) {

          return GameMessage.notInGameboard;
       }


       //tower must not be completed
       else if(gameboard.getTowerCell(x,y).isTowerCompleted()==true) {

          return GameMessage.noBuildToCompleteTower;
       }

       //workerPosition must be adjacent to buildingPosition
       else if (!workerStartingPosition.adjacent(x,y)){

          return GameMessage.notInSurroundings;
       }

       //the chosen piece must not be a "Block" when tower's height is 3
       else if(gameboard.getTowerCell(x,y).getTowerHeight()==3 &&  message.getPieceType().equals("Block")){

           return GameMessage.noBlocksInDome;
       }

       //the chosen piece must not be a "Dome" when tower's height is <3
       else if(gameboard.getTowerCell(x,y).getTowerHeight()<3 &&  message.getPieceType().equals("Dome")) {

           return GameMessage.noDomesInBlock;
       }

       //control whether there are pieces left
       else if (gameboard.getTowerCell(x,y).getTowerHeight()==0 && Level1Block.areTherePiecesLeft()==false){

          return GameMessage.noLevel1Left;
       }

       else if (gameboard.getTowerCell(x,y).getTowerHeight()==1 && Level2Block.areTherePiecesLeft()==false){

           return GameMessage.noLevel2Left;
       }

       else if (gameboard.getTowerCell(x,y).getTowerHeight()==2 && Level3Block.areTherePiecesLeft()==false){

           return GameMessage.noLevel3Left;
       }

       else if (gameboard.getTowerCell(x,y).getTowerHeight()==3 && Dome.areTherePiecesLeft()==false){

           return GameMessage.noDomesLeft;
       }

       //there must not be a worker in the building position
       else if (gameboard.getTowerCell(x,y).hasWorkerOnTop()==true){

           return GameMessage.noBuildToOccupiedTower;
       }

       else return GameMessage.buildOK;

   }


    /**
     * changes tower's height and checks if tower is complete
     *
     * @param gameboard
     * @param message message PlayerBuildChoice
     */
    @Override
    public void build(GameBoard gameboard, PlayerBuildChoice message) {


        int x = message.getBuildingInto()[0];
        int y = message.getBuildingInto()[1];
        int z = gameboard.getTowerCell(x, y).getTowerHeight();
        Piece piece;

       //create the right Piece and decrease the number

       if (gameboard.getTowerCell(x,y).getTowerHeight()==0){
           piece = new Level1Block();
           
       }
       else if (gameboard.getTowerCell(x,y).getTowerHeight()==1){
           piece = new Level2Block();
       }
       else if (gameboard.getTowerCell(x,y).getTowerHeight()==2){
           piece = new Level3Block();
       }
       else if (gameboard.getTowerCell(x,y).getTowerHeight()==3){
           piece = new Dome();
       }

       //set the Piece
        gameboard.getTowerCell(x,y).getFirstUnoccupiedTowerLevel().setPiece(piece);

       //increase tower's height
        gameboard.getTowerCell(x, y).increaseTowerHeight();

       //check if tower is complete
        gameboard.getTowerCell(x,y).checkCompletion();

        this.alreadyMoved = true;
        //TODO notify()-> spedire messaggio con copia delle informazioni utili dello stato della board

        return ;




    }
}
