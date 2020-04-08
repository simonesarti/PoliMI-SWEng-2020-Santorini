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


        Position workerStartingPosition = new Position(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY(),worker.getCurrentPosition().getZ());

        //alreadyMoved must be false
        if(alreadyMoved){
            return GameMessage.alreadyMoved;
        }
        //x and y must be inside the board
        else if (x < 0 || x > 4 || y < 0 || y > 4) {


            return GameMessage.notInSurroundings;
        }

        //towercell must be empty
        else if(gameboard.getTowerCell(x,y).getFirstUnoccupiedTowerLevel().isOccupied()){


            return GameMessage.noMovedToOccupiedTower;
        }


        //towercell height must be <= (worker height +1)
        else if(gameboard.getTowerCell(x,y).getTowerHeight() > (worker.getCurrentPosition().getZ() +1)) {

            return GameMessage.noHighJump;
        }

        //workerPosition must be adjacent to destinationPosition
        else if (!workerStartingPosition.adjacent(x,y)){

            return GameMessage.notInSurroundings;
        }

        //towerCell must not be completed by a dome
        else if (!gameboard.getTowerCell(x,y).isTowerCompleted()){


            return GameMessage.noMoveToCompleteTower;
        }

        else return GameMessage.moveOK;

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
        Position workerStartingPosition = new Position(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY(),worker.getCurrentPosition().getZ());

        //getting selected worker to the new towerCell
        gameboard.getTowerCell(workerStartingPosition.getX(), workerStartingPosition.getY()).getFirstUnoccupiedTowerLevel().workerMoved();
        gameboard.getTowerCell(x, y).getFirstUnoccupiedTowerLevel().setWorker(worker);

        //modifing worker's associated position
        worker.movedToPosition(x,y,z);
        this.alreadyMoved = true;
        //TODO notify()-> spedire messaggio con copia delle informazioni utili dello stato della board


    }


    @Override
    public boolean getAlreadyMoved() {
        return this.alreadyMoved;
    }
}
