package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.PlayerMovementChoice;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.Worker;

/**
 * This class represents the basic move strategy
 */
public class BasicMove implements MoveStrategy {

    private boolean alreadyMoved;


    public BasicMove(){
        alreadyMoved = false;
    }
    /**
     * Checks if player's move is feasible
     *
     * @param gameboard
     * @param message message PlayerMovementChoice
     * @return
     */
    @Override
    public String checkMove(GameBoard gameboard, PlayerMovementChoice message){

        Worker worker = message.getPlayer().getWorker(message.getChosenWorker());
        int x = message.getMovingTo()[0];
        int y = message.getMovingTo()[1];
        int z = gameboard.getTowerCell(x, y).getTowerHeight();

        //alreadyMoved must be false
        if(alreadyMoved){
            return GameMessage.alreadyMoved;
        }

        //x and y must be inside the board
        if (x < 0 || x > 4 || y < 0 || y > 4) {
            return GameMessage.notInGameboard;
        }

        //workerPosition must not be the destination position
        if (worker.getCurrentPosition().getX()==x && worker.getCurrentPosition().getY()==y){
            return GameMessage.notTheSame;
        }
        //workerPosition must be adjacent to destination position
        if (!worker.getCurrentPosition().adjacent(x,y)){
            return GameMessage.notInSurroundings;
        }

        //towerCell must not be completed by a dome
        if (!gameboard.getTowerCell(x,y).isTowerCompleted()){
            return GameMessage.noMoveToCompleteTower;
        }

        //towercell height must be <= (worker height +1)
        if(gameboard.getTowerCell(x,y).getTowerHeight() > (worker.getCurrentPosition().getZ() +1)) {
            return GameMessage.noHighJump;
        }

        //towercell must be empty
        if(gameboard.getTowerCell(x,y).hasWorkerOnTop()){
            return GameMessage.noMovedToOccupiedTower;
        }

        return GameMessage.moveOK;

    }

    /**
     * Removes worker from starting towercell's towerlevel, changes worker's position values and sets the worker in the
     * destination's towercell's towerlevel
     *
     * @param gameboard
     * @param message message PlayerMovementChoice
     */
    @Override
    public void move(GameBoard gameboard, PlayerMovementChoice message) {

        Worker worker = message.getPlayer().getWorker(message.getChosenWorker());
        int x = message.getMovingTo()[0];
        int y = message.getMovingTo()[1];
        int z = gameboard.getTowerCell(x, y).getTowerHeight();

        //getting selected worker to the new towerCell
        gameboard.getTowerCell(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY()).getFirstNotPieceLevel().workerMoved();
        gameboard.getTowerCell(x, y).getFirstNotPieceLevel().setWorker(worker);

        //modifying worker's associated position
        worker.movedToPosition(x,y,z);
        this.alreadyMoved = true;
        //TODO notify()-> spedire messaggio con copia delle informazioni utili dello stato della board


    }


    @Override
    public boolean getAlreadyMoved() {
        return this.alreadyMoved;
    }
}
