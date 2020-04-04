package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.Worker;

public class BasicMove implements MoveStrategy {

    /**
     * Rimuove il worker dalla towerCell iniziale, lo aggiunge alla towerCell in posizione x,y e cambia la posizione
     * associata al worker
     *
     * @param gameboard
     * @param worker worker scelto per il turno
     * @param x x della towerCell in cui il player ha deciso di muoversi
     * @param y y della towerCell in cui il player ha deciso di muoversi
     */
    @Override
    public void move(GameBoard gameboard, Worker worker, int x, int y) {

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
            //worker position values are modified

            worker.movedToPosition(destinationPosition);
            worker.trimMovementHistory(); //TODO devo trimmare la coda di movimenti alla fine della basic move?

            return ;
        }

    }
}
