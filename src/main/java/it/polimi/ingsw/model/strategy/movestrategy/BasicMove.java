package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.PlayerMovementChoice;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.Worker;

public class BasicMove implements MoveStrategy {

    /**
     * Removes worker from starting towercell's towerlevel, changes worker's position values and sets the worker in the
     * destination's towercell's towerlevel
     *
     * @param gameboard
     * @param message messaggio PlayerMovementChoice
     */
    @Override
    public void move(GameBoard gameboard, PlayerMovementChoice message) {


        Worker worker = message.getPlayer().getWorker(message.getChosenWorker());
        int x = message.getMovingTo()[0];
        int y = message.getMovingTo()[1];
        Position destinationPosition = new Position(x, y, gameboard.getTowerCell(x, y).getTowerHeight());
        Position workerStartingPosition = new Position(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY(),worker.getCurrentPosition().getZ());

        //controllo feasible basic move
        if (x < 0 || x > 4 || y < 0 || y > 4) {

            //TODO notify() -> spedire messaggio errore alla view
            return ;
        }

        //towercell must be empty
        else if(gameboard.getTowerCell(x,y).getTowerLevel().isOccupied()){

            //TODO notify() -> spedire messaggio errore alla view
            return;
        }


        //towercell height must be <= (worker height +1)
        else if(gameboard.getTowerCell(x,y).getTowerHeight() > (worker.getCurrentPosition().getZ() +1)) {
            //TODO notify() -> spedire messaggio errore alla view
            return;
        }

        //workerPosition must be adjacent to destinationPosition
        else if (!workerStartingPosition.adjacent(destinationPosition)){
            //TODO notify() -> spedire messaggio errore alla view
            return;
        }

        //towerCell must not be completed by a dome
        else if (!gameboard.getTowerCell(x,y).isTowerCompleted()){

            //TODO notify() -> spedire messaggio errore alla view
            return;
        }

        else {
            //getting selected worker to the new towerCell
            gameboard.getTowerCell(workerStartingPosition.getX(), workerStartingPosition.getY()).getTowerLevel().workerMoved();
            gameboard.getTowerCell(x, y).getTowerLevel().setWorker(worker);

            //modifing worker's associated position
            worker.movedToPosition(destinationPosition);
            worker.trimMovementHistory(); //TODO devo trimmare la coda di movimenti alla fine della basic move?

            //TODO notify()-> spedire messaggio con copia delle informazioni utili dello stato della board

            return ;
        }

    }
}
