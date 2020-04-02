package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.Worker;

public class BasicMove implements MoveStrategy {


    @Override
    public void move(GameBoard gameboard, Worker worker, int x, int y) {

        //getting selected worker to the new towerCell
        gameboard.getTowerCell(worker.getCurrentPosition().getX(),worker.getCurrentPosition().getX()).getTowerLevel().workerMoved();
        gameboard.getTowerCell(x,y).getTowerLevel().setWorker(worker);
        //worker position values are modified
        Position position = new Position(x,y,gameboard.getTowerCell(x,y).getTowerHeight());
        worker.movedToPosition(position);
        //TODO basta chiamare la movedToPosition() o bisogna fare altro per cambiare la posizione del worker???


    }
}
