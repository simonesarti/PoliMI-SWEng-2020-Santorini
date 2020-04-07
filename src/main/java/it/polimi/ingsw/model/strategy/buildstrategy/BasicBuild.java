package it.polimi.ingsw.model.strategy.buildstrategy;

import it.polimi.ingsw.messages.PlayerMovementChoice;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.piece.Block;
import it.polimi.ingsw.model.piece.Piece;
import it.polimi.ingsw.messages.PlayerBuildChoice;


public class BasicBuild implements BuildStrategy {

    /**
     * Checks if player can build
     *
     * @param gameboard
     * @param message message PlayerBuildChoice
     * @return
     */


   public boolean checkBuild(GameBoard gameboard, PlayerBuildChoice message){

       Worker worker = message.getPlayer().getWorker(message.getChosenWorker());
       int x = message.getBuildingInto()[0];
       int y = message.getBuildingInto()[1];
       int z = gameboard.getTowerCell(x, y).getTowerHeight();

       Position workerStartingPosition = new Position(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY(),worker.getCurrentPosition().getZ());


       //x e y must be inside the board
       if (x < 0 || x > 4 || y < 0 || y > 4) {

           return false;
       }


       //tower must not be completed
       else if(gameboard.getTowerCell(x,y).isTowerCompleted()==true) {

           return false;
       }

       //workerPosition must be adjacent to buildingPosition
       else if (!workerStartingPosition.adjacent(x,y)){

           return false;
       }

       //the chosen piece must not be a "Block" when tower's height is 3
       else if(gameboard.getTowerCell(x,y).getTowerHeight()==3 &&  message.getPieceType().equals("Block")){

           return false;
       }

       //the chosen piece must not be a "Dome" when tower's height is <3
       else if(gameboard.getTowerCell(x,y).getTowerHeight()<3 &&  message.getPieceType().equals("Dome")) {

           return false;
       }

       //TODO controllare che esistano ancora pezzi disponibili

       else return true;

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

       //TODO istanziare pezzo di tipo Piece e inserirlo

        //increase tower's height
        gameboard.getTowerCell(x, y).increaseTowerHeight();

        //check if tower is completed
        gameboard.getTowerCell(x,y).checkCompletion();

        //TODO notify()-> spedire messaggio con copia delle informazioni utili dello stato della board

        return ;




    }
}
