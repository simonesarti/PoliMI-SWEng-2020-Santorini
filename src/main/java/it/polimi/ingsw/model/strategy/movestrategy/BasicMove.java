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
        //controllo feasible basic move
        if (x < 0 || x > 4 || y < 0 || y > 4) {
            return ;
        }
        //TODO riportare errore alla view

        //towercell must be empty
        else if(gameboard.getTowerCell(x,y).getTowerLevel().isOccupied()) return;
        //TODO riportare errore alla view

        //towercell height must be <= (worker height +1)
        else if(gameboard.getTowerCell(x,y).getTowerHeight() > (worker.getCurrentPosition().getZ() +1)) return ;
        //TODO riportare errore alla view

        else {
            //getting selected worker to the new towerCell
            gameboard.getTowerCell(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY()).getTowerLevel().workerMoved();
            gameboard.getTowerCell(x, y).getTowerLevel().setWorker(worker);
            //worker position values are modified
            Position position = new Position(x, y, gameboard.getTowerCell(x, y).getTowerHeight());
            worker.movedToPosition(position);
            //TODO basta chiamare la movedToPosition() o bisogna fare altro per cambiare la posizione del worker???
            return ;
        }

    }
}
